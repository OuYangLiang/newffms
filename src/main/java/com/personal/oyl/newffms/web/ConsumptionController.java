package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.constants.ConsumptionType;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.pojo.JqGridJsonRlt;
import com.personal.oyl.newffms.pojo.validator.ConsumptionFormValidator;
import com.personal.oyl.newffms.service.AccountService;
import com.personal.oyl.newffms.service.CategoryService;
import com.personal.oyl.newffms.service.ConsumptionItemService;
import com.personal.oyl.newffms.service.ConsumptionService;
import com.personal.oyl.newffms.service.TransactionService;
import com.personal.oyl.newffms.service.UserProfileService;
import com.personal.oyl.newffms.util.SessionUtil;

@Controller
@RequestMapping("/consumption")
public class ConsumptionController {
    
    private static final String SESSION_KEY_SEARCH_PARAM_CONSUMPTION = "SESSION_KEY_SEARCH_PARAM_CONSUMPTION";
    
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ConsumptionService consumptionService;
    @Autowired
    private ConsumptionItemService consumptionItemService;
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ConsumptionFormValidator consumptionFormValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(consumptionFormValidator);
    }
    
    @RequestMapping("summary")
    public String summary() throws SQLException {

        return "consumption/summary";
    }
    
    @RequestMapping("/listOfSummary")
    @ResponseBody
    public JqGridJsonRlt listOfSummary(@RequestParam("requestPage") int requestPage, @RequestParam("sizePerPage") int sizePerPage,
            @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, HttpSession session) throws SQLException {
        
        Consumption searchParam = (Consumption) session.getAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTION);
        
        if (null == searchParam) {
            searchParam = new Consumption();
            searchParam.setBaseObject(new BaseObject());
            session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTION, searchParam);
        }
        
        searchParam.setSizePerPage(sizePerPage);
        searchParam.setStart( (requestPage - 1) * sizePerPage );
        //searchParam.getBaseObject().setSortField(sortField);
        //searchParam.getBaseObject().setSortDir(sortDir);
        //searchParam.getBaseObject().setRequestPage(requestPage);
        
        int count = consumptionService.getCountOfSummary(searchParam);
        
        List<Consumption> list = consumptionService.getListOfSummary(searchParam);
        
        
        JqGridJsonRlt rlt = new JqGridJsonRlt();
        rlt.setRows(list);
        rlt.setPage(requestPage);
        rlt.setRecords(count);
        rlt.setTotal(BigDecimal.valueOf(count).divide(BigDecimal.valueOf(sizePerPage), BigDecimal.ROUND_UP).intValue());
        
        return rlt;
    }
    
    @RequestMapping("/listOfItemSummary")
    @ResponseBody
    public JqGridJsonRlt listOfItemSummary(@RequestParam("cpnOid") BigDecimal cpnOid) throws SQLException {
        
        List<ConsumptionItem> list = consumptionItemService.queryConsumptionItemByCpn(cpnOid);
        
        for (ConsumptionItem item : list) {
            item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }
        
        JqGridJsonRlt rlt = new JqGridJsonRlt();
        rlt.setRows(list);
        rlt.setPage(1);
        rlt.setRecords(list.size());
        rlt.setTotal(1);
        
        return rlt;
    }
    
    @RequestMapping("/initAdd")
    public String initAdd(Model model, HttpSession session) throws SQLException {
        
        ConsumptionForm form = null;
        
        if (null != session.getAttribute("cpnForm")) {
            form = (ConsumptionForm) session.getAttribute("cpnForm");
        }
        else {
            form = new ConsumptionForm();
        }
        
        model.addAttribute("cpnForm", form);
        model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
        model.addAttribute("users", userProfileService.select(null));
        
        return "consumption/add";
    }
    
    
    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("cpnForm") ConsumptionForm form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
            model.addAttribute("users", userProfileService.select(null));
            model.addAttribute("validation", false);
            
            return "consumption/add";
        }
        
        form.getConsumption().calculateCpnTime();
        form.getConsumption().setCpnTypeDesc(form.getConsumption().getCpnType().getDesc());
        
        for ( ConsumptionItem item : form.getCpnItems() ) {
            item.setUserName(userProfileService.selectByKey(item.getOwnerOid()).getUserName());
            item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }
        
        session.setAttribute("cpnForm", form);
        
        return "consumption/confirmAdd";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) throws SQLException {
        ConsumptionForm form = (ConsumptionForm) session.getAttribute("cpnForm");
        
        form.getConsumption().setAmount(form.getTotalItemAmount());
        form.getConsumption().setConfirmed(false);
        BaseObject base = new BaseObject();
        base.setCreateTime(new Date());
        base.setCreateBy(SessionUtil.getLoginUser(session).getUserName());
        
        form.getConsumption().setBaseObject(base);
        
        transactionService.createConsumption(form);
        
        session.removeAttribute("cpnForm");
        
        return "consumption/summary";
    }
    
    @RequestMapping("/view")
    public String view(@RequestParam("cpnOid") BigDecimal cpnOid, Model model) throws SQLException {
        ConsumptionForm form = new ConsumptionForm();
        
        Consumption consumption = consumptionService.selectByKey(cpnOid);
        List<ConsumptionItem> cItems = consumptionItemService.queryConsumptionItemByCpn(cpnOid);
        List<Account> acntItems = accountService.queryAccountsByConsumption(cpnOid);
        
        form.setConsumption(consumption);
        form.setCpnItems(cItems);
        form.setAccounts(acntItems);
        
        form.getConsumption().setCpnTypeDesc(form.getConsumption().getCpnType().getDesc());
        
        for ( ConsumptionItem item : form.getCpnItems() ) {
            item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }
        
        model.addAttribute("cpnForm", form);
        
        return "consumption/view";
    }
    
    @RequestMapping("/initEdit")
    public String initEdit(@RequestParam(value="cpnOid", required=false) BigDecimal cpnOid, Model model, HttpSession session) throws SQLException {
        
        ConsumptionForm form = null;
        
        if (null != session.getAttribute("cpnForm")) {
            form = (ConsumptionForm) session.getAttribute("cpnForm");
        }
        else {
            form = new ConsumptionForm();
            
            Consumption consumption = consumptionService.selectByKey(cpnOid);
            List<ConsumptionItem> cItems = consumptionItemService.queryConsumptionItemByCpn(cpnOid);
            List<Account> acntItems = accountService.queryAccountsByConsumption(cpnOid);
            
            form.setConsumption(consumption);
            form.setCpnItems(cItems);
            form.setAccounts(acntItems);
        }
        
        model.addAttribute("cpnForm", form);
        model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
        model.addAttribute("users", userProfileService.select(null));
        
        return "consumption/edit";
    }
    
    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("cpnForm") ConsumptionForm form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
            model.addAttribute("users", userProfileService.select(null));
            model.addAttribute("validation", false);
            
            return "consumption/edit";
        }
        
        form.getConsumption().calculateCpnTime();
        form.getConsumption().setCpnTypeDesc(form.getConsumption().getCpnType().getDesc());
        
        for ( ConsumptionItem item : form.getCpnItems() ) {
            item.setUserName(userProfileService.selectByKey(item.getOwnerOid()).getUserName());
            item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }
        
        session.setAttribute("cpnForm", form);
        
        return "consumption/confirmEdit";
    }
    
    @RequestMapping("/saveEdit")
    public String saveEdit(Model model, HttpSession session) throws SQLException {
        ConsumptionForm form = (ConsumptionForm) session.getAttribute("cpnForm");
        
        form.getConsumption().setAmount(form.getTotalItemAmount());
        
        Consumption oldObj = consumptionService.selectByKey(form.getConsumption().getCpnOid());
        form.getConsumption().setBaseObject(new BaseObject());
        form.getConsumption().getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        form.getConsumption().getBaseObject().setUpdateBy(SessionUtil.getLoginUser(session).getUserName());
        form.getConsumption().getBaseObject().setUpdateTime(new Date());
        
        transactionService.updateConsumption(form);
        
        session.removeAttribute("cpnForm");
        
        return "consumption/summary";
    }
    
    @RequestMapping("/delete")
    public String delete(@RequestParam("cpnOid") BigDecimal cpnOid, Model model) throws SQLException {
        transactionService.deleteConsumption(cpnOid);
        
        return "consumption/summary";
    }
    
    @RequestMapping("/confirm")
    public String confirm(@RequestParam("cpnOid") BigDecimal cpnOid, Model model, HttpSession session) throws SQLException {
        transactionService.confirmConsumption(cpnOid, SessionUtil.getLoginUser(session).getUserName());
        
        return "consumption/summary";
    }
    
    @RequestMapping("/rollback")
    public String rollback(@RequestParam("cpnOid") BigDecimal cpnOid, Model model, HttpSession session) throws SQLException {
        transactionService.rollbackConsumption(cpnOid, SessionUtil.getLoginUser(session).getUserName());
        
        return "consumption/summary";
    }
}
