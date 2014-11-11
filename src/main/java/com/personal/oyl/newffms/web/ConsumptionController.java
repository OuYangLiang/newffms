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

import com.personal.oyl.newffms.constants.ConsumptionType;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.pojo.validator.ConsumptionFormValidator;
import com.personal.oyl.newffms.util.SessionUtil;

@Controller
@RequestMapping("/consumption")
public class ConsumptionController extends BaseController{
    
    //private static final String SESSION_KEY_SEARCH_PARAM_CONSUMPTION = "SESSION_KEY_SEARCH_PARAM_CONSUMPTION";
    
    @Autowired
    private ConsumptionFormValidator consumptionFormValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(consumptionFormValidator);
    }
    
    /*@RequestMapping("summary")
    public String summary(HttpServletRequest request, HttpSession session) throws SQLException {
        this.clearSearchParameter(request, session, SESSION_KEY_SEARCH_PARAM_CONSUMPTION);
        
        //初始化查询条件。
        
        //设置默认查询条件值，并放入session中
        
        Consumption searchParam = (Consumption) session.getAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTION);
        
        if (null == searchParam) {
            searchParam = new Consumption();
            searchParam.setCpnTimeFrom(DateUtil.getInstance().getFirstTimeOfLastMonth());
            searchParam.setCpnTimeTo(DateUtil.getInstance().getEndTime(new Date()));
            
            searchParam.setRequestPage(1);
            searchParam.setSizePerPage(10);
            searchParam.setSortField("CPN_TIME");
            searchParam.setSortDir("desc");
            
            session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTION, searchParam);
        }

        return "consumption/summary";
    }
    
    @RequestMapping("search")
    @ResponseBody
	public String search(@RequestParam("cpnTimeFrom") Date cpnTimeFrom,
			@RequestParam("cpnTimeTo") Date cpnTimeTo,
			@RequestParam("confirmed") Boolean confirmed, HttpSession session) {
    	//从页面接受查询参数，并放入session中。
        Consumption searchParam = new Consumption();
        searchParam.setCpnTimeFrom(DateUtil.getInstance().getBeginTime(cpnTimeFrom));
        searchParam.setCpnTimeTo(DateUtil.getInstance().getEndTime(cpnTimeTo));
        searchParam.setConfirmed(confirmed);
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTION, searchParam);
        
        return "OK";
    }
    
    @RequestMapping("/listOfSummary")
    @ResponseBody
    public JqGridJsonRlt<Consumption> listOfSummary(@RequestParam(value = "requestPage", required = true) int requestPage,
            @RequestParam(value = "sizePerPage", required = true) int sizePerPage,
            @RequestParam(value = "sortField", required = true) String sortField,
            @RequestParam(value = "sortDir", required = true) String sortDir, HttpSession session) throws SQLException {
        
        //从session中取出查询对象并查询

        Consumption searchParam = (Consumption) session.getAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTION);
        
        searchParam.setStart( (requestPage - 1) * sizePerPage );
        searchParam.setSizePerPage(sizePerPage);
        searchParam.setRequestPage(requestPage);
        
        if (sortField != null && !sortField.trim().isEmpty()) {
            searchParam.setSortField(sortField);
            searchParam.setSortDir(sortDir);
        }
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_CONSUMPTION, searchParam);
        
        return this.initPaging(consumptionService, searchParam);
    }
    
    @RequestMapping("/listOfItemSummary")
    @ResponseBody
    public JqGridJsonRlt<ConsumptionItem> listOfItemSummary(@RequestParam("cpnOid") BigDecimal cpnOid) throws SQLException {
        
        List<ConsumptionItem> list = consumptionItemService.queryConsumptionItemByCpn(cpnOid);
        
        for (ConsumptionItem item : list) {
            item.setCategoryFullDesc(categoryService.selectFullDescByKey(item.getCategoryOid()));
        }
        
        JqGridJsonRlt<ConsumptionItem> rlt = new JqGridJsonRlt<ConsumptionItem>();
        rlt.setRows(list);
        rlt.setPage(1);
        rlt.setRecords(list.size());
        rlt.setTotal(1);
        
        return rlt;
    }*/
    
    @RequestMapping("/initAdd")
    public String initAdd(@RequestParam(value = "back", required = false) Boolean back, Model model, HttpSession session) throws SQLException {
        
        ConsumptionForm form = null;
        
        if (null != back && back && null != session.getAttribute("cpnForm")) {
            form = (ConsumptionForm) session.getAttribute("cpnForm");
        }
        else {
            form = new ConsumptionForm();
        }
        
        model.addAttribute("cpnForm", form);
        model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        return "consumption/add";
    }
    
    
    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("cpnForm") ConsumptionForm form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
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
        base.setCreateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        
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
	public String initEdit(@RequestParam(value = "back", required = false) Boolean back,
			@RequestParam(value = "cpnOid", required = false) BigDecimal cpnOid,
			Model model, HttpSession session) throws SQLException {
  
        ConsumptionForm form = null;
        
        if (null != back && back && null != session.getAttribute("cpnForm")) {
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
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        return "consumption/edit";
    }
    
    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("cpnForm") ConsumptionForm form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("cpnTypes", ConsumptionType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
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
        form.getConsumption().getBaseObject().setUpdateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        form.getConsumption().getBaseObject().setUpdateTime(new Date());
        
        transactionService.updateConsumption(form);
        
        session.removeAttribute("cpnForm");
        
        return "consumption/summary";
    }
    
    @RequestMapping("/delete")
    public String delete(@RequestParam("cpnOid") BigDecimal cpnOid, Model model) throws SQLException {
        transactionService.deleteConsumption(cpnOid);
        
        return "redirect:/consumption/summary?keepSp=Y";
    }
    
    @RequestMapping("/confirm")
    public String confirm(@RequestParam("cpnOid") BigDecimal cpnOid, Model model, HttpSession session) throws SQLException {
        transactionService.confirmConsumption(cpnOid, SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        return "redirect:/consumption/summary?keepSp=Y";
    }
    
    @RequestMapping("/rollback")
    public String rollback(@RequestParam("cpnOid") BigDecimal cpnOid, Model model, HttpSession session) throws SQLException {
        transactionService.rollbackConsumption(cpnOid, SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        return "redirect:/consumption/summary?keepSp=Y";
    }
}
