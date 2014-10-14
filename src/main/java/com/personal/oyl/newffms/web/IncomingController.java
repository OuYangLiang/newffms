package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
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

import com.personal.oyl.newffms.constants.IncomingType;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.pojo.JqGridJsonRlt;
import com.personal.oyl.newffms.pojo.validator.IncomingValidator;
import com.personal.oyl.newffms.util.SessionUtil;

@Controller
@RequestMapping("/incoming")
public class IncomingController extends BaseController{
    private static final String SESSION_KEY_SEARCH_PARAM_INCOMING = "SESSION_KEY_SEARCH_PARAM_INCOMING";
    
    @Autowired
    private IncomingValidator incomingValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(incomingValidator);
    }
    
    @RequestMapping("summary")
    public String summary(HttpServletRequest request, Model model, HttpSession session) throws SQLException {
        this.clearSearchParameter(request, session, SESSION_KEY_SEARCH_PARAM_INCOMING);
        
        //初始化查询条件。
        
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        //设置默认查询条件值，并放入session中
        
        Incoming searchParam = (Incoming) session.getAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING);
        
        if (null == searchParam) {
            searchParam = new Incoming();
            
            searchParam.setRequestPage(1);
            searchParam.setSizePerPage(10);
            searchParam.setSortField("INCOMING_DATE");
            searchParam.setSortDir("desc");
            
            session.setAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING, searchParam);
        }

        return "incoming/summary";
    }
    
    @RequestMapping("search")
    @ResponseBody
    public String search(@RequestParam("ownerOid") BigDecimal ownerOid, HttpSession session) {
        //从页面接受查询参数，并放入session中。
        Incoming searchParam = new Incoming();
        
        searchParam.setOwnerOid(ownerOid);
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING, searchParam);
        
        return "OK";
    }
    
    @RequestMapping("/listOfSummary")
    @ResponseBody
    public JqGridJsonRlt<Incoming> listOfSummary(@RequestParam(value = "requestPage", required = true) int requestPage,
            @RequestParam(value = "sizePerPage", required = true) int sizePerPage,
            @RequestParam(value = "sortField", required = true) String sortField,
            @RequestParam(value = "sortDir", required = true) String sortDir, HttpSession session) throws SQLException {
        
        //从session中取出查询对象并查询

        Incoming searchParam = (Incoming) session.getAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING);
        
        searchParam.setStart( (requestPage - 1) * sizePerPage );
        searchParam.setSizePerPage(sizePerPage);
        searchParam.setRequestPage(requestPage);
        
        if (sortField != null && !sortField.trim().isEmpty()) {
            searchParam.setSortField(sortField);
            searchParam.setSortDir(sortDir);
        }
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_INCOMING, searchParam);
        
        return this.initPaging(incomingService, searchParam);
    }
    
    @RequestMapping("/initAdd")
    public String initAdd(Model model, HttpSession session) throws SQLException {
        
        Incoming form = null;
        
        if (null != session.getAttribute("incomingForm")) {
            form = (Incoming) session.getAttribute("incomingForm");
        }
        else {
            form = new Incoming();
        }
        
        model.addAttribute("incomingForm", form);
        model.addAttribute("incomingTypes", IncomingType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        return "incoming/add";
    }
    
    
    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("incomingForm") Incoming form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("incomingTypes", IncomingType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
            model.addAttribute("validation", false);
            
            return "incoming/add";
        }
        
        form.setOwner(userProfileService.selectByKey(form.getOwnerOid()).getUserName());
        
        session.setAttribute("incomingForm", form);
        
        return "incoming/confirmAdd";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) throws SQLException {
        Incoming form = (Incoming) session.getAttribute("incomingForm");
        
        form.setConfirmed(false);
        BaseObject base = new BaseObject();
        base.setCreateTime(new Date());
        base.setCreateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        form.setBaseObject(base);
        
        transactionService.createIncoming(form);
        
        session.removeAttribute("incomingForm");
        
        return "incoming/summary";
    }
    
    @RequestMapping("/view")
    public String view(@RequestParam("incomingOid") BigDecimal incomingOid, Model model) throws SQLException {
        Incoming form = incomingService.selectByKey(incomingOid);
        Account acnt = accountService.queryAccountsByIncoming(incomingOid);
        
        form.setOwner(userProfileService.selectByKey(form.getOwnerOid()).getUserName());
        form.setAcntOid(acnt.getAcntOid());
        form.setAcntHumanDesc(acnt.getAcntHumanDesc());
        
        model.addAttribute("incomingForm", form);
        
        return "incoming/view";
    }
    
    @RequestMapping("/initEdit")
    public String initEdit(@RequestParam(value="incomingOid", required=false) BigDecimal incomingOid, Model model, HttpSession session) throws SQLException {
        
        Incoming form = null;
        
        if (null != session.getAttribute("incomingForm")) {
            form = (Incoming) session.getAttribute("incomingForm");
        }
        else {
            form = incomingService.selectByKey(incomingOid);
            Account acnt = accountService.queryAccountsByIncoming(incomingOid);
            
            form.setAcntOid(acnt.getAcntOid());
            form.setAcntHumanDesc(acnt.getAcntHumanDesc());
        }
        
        model.addAttribute("incomingForm", form);
        model.addAttribute("incomingTypes", IncomingType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        return "incoming/edit";
    }
    
    @RequestMapping("/confirmEdit")
    public String confirmEdit(@Valid @ModelAttribute("incomingForm") Incoming form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("incomingTypes", IncomingType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
            model.addAttribute("validation", false);
            
            return "incoming/edit";
        }
        
        form.setOwner(userProfileService.selectByKey(form.getOwnerOid()).getUserName());
        
        session.setAttribute("incomingForm", form);
        
        return "incoming/confirmEdit";
    }
    
    @RequestMapping("/saveEdit")
    public String saveEdit(Model model, HttpSession session) throws SQLException {
        Incoming form = (Incoming) session.getAttribute("incomingForm");
        
        Incoming oldObj = incomingService.selectByKey(form.getIncomingOid());
        form.setBaseObject(new BaseObject());
        form.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        form.getBaseObject().setUpdateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        form.getBaseObject().setUpdateTime(new Date());
        
        transactionService.updateIncoming(form);
        
        session.removeAttribute("incomingForm");
        
        return "incoming/summary";
    }
    
    @RequestMapping("/delete")
    public String delete(@RequestParam("incomingOid") BigDecimal incomingOid, Model model) throws SQLException {
        transactionService.deleteIncoming(incomingOid);
        
        return "redirect:/incoming/summary?keepSp=Y";
    }
    
    @RequestMapping("/confirm")
    public String confirm(@RequestParam("incomingOid") BigDecimal incomingOid, Model model, HttpSession session) throws SQLException {
        transactionService.confirmIncoming(incomingOid, SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        return "redirect:/incoming/summary?keepSp=Y";
    }
    
    @RequestMapping("/rollback")
    public String rollback(@RequestParam("incomingOid") BigDecimal incomingOid, Model model, HttpSession session) throws SQLException {
        transactionService.rollbackIncoming(incomingOid, SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        return "redirect:/incoming/summary?keepSp=Y";
    }
}
