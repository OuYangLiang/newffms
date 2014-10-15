package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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

import com.personal.oyl.newffms.constants.AccountType;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.JqGridJsonRlt;
import com.personal.oyl.newffms.pojo.validator.AccountValidator;
import com.personal.oyl.newffms.util.SessionUtil;

@Controller
@RequestMapping("/account")
public class AccountController extends BaseController{
    
    private static final String SESSION_KEY_SEARCH_PARAM_ACCOUNT = "SESSION_KEY_SEARCH_PARAM_ACCOUNT";
    
    @Autowired
    private AccountValidator accountValidator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(accountValidator);
    }
    
    @RequestMapping("summary")
    public String summary(HttpServletRequest request, Model model, HttpSession session) throws SQLException {
        this.clearSearchParameter(request, session, SESSION_KEY_SEARCH_PARAM_ACCOUNT);
        
        //初始化查询条件。
        
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        //设置默认查询条件值，并放入session中
        
        Account searchParam = (Account) session.getAttribute(SESSION_KEY_SEARCH_PARAM_ACCOUNT);
        
        if (null == searchParam) {
            searchParam = new Account();
            
            searchParam.setRequestPage(1);
            searchParam.setSizePerPage(10);
            searchParam.setSortField("OWNER_OID");
            searchParam.setSortDir("asc");
            
            session.setAttribute(SESSION_KEY_SEARCH_PARAM_ACCOUNT, searchParam);
        }

        return "account/summary";
    }
    
    @RequestMapping("search")
    @ResponseBody
    public String search(@RequestParam("ownerOid") BigDecimal ownerOid, HttpSession session) {
        //从页面接受查询参数，并放入session中。
        Account searchParam = new Account();
        searchParam.setOwnerOid(ownerOid);
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_ACCOUNT, searchParam);
        
        return "OK";
    }
    
    @RequestMapping("/listOfSummary")
    @ResponseBody
    public JqGridJsonRlt<Account> listOfSummary(@RequestParam(value = "requestPage", required = true) int requestPage,
            @RequestParam(value = "sizePerPage", required = true) int sizePerPage,
            @RequestParam(value = "sortField", required = true) String sortField,
            @RequestParam(value = "sortDir", required = true) String sortDir, HttpSession session) throws SQLException {
        
        //从session中取出查询对象并查询

        Account searchParam = (Account) session.getAttribute(SESSION_KEY_SEARCH_PARAM_ACCOUNT);
        
        searchParam.setStart( (requestPage - 1) * sizePerPage );
        searchParam.setSizePerPage(sizePerPage);
        searchParam.setRequestPage(requestPage);
        
        if (sortField != null && !sortField.trim().isEmpty()) {
            searchParam.setSortField(sortField);
            searchParam.setSortDir(sortDir);
        }
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_ACCOUNT, searchParam);
        
        return this.initPaging(accountService, searchParam);
    }
    
    @RequestMapping("/initAdd")
    public String initAdd(Model model, HttpSession session) throws SQLException {
        
        Account form = null;
        
        if (null != session.getAttribute("acntForm")) {
            form = (Account) session.getAttribute("acntForm");
        }
        else {
            form = new Account();
        }
        
        model.addAttribute("acntForm", form);
        model.addAttribute("acntTypes", AccountType.toMapValue());
        model.addAttribute("users", userProfileService.selectAllUsers());
        
        return "account/add";
    }
    
    
    @RequestMapping("/confirmAdd")
    public String confirmAdd(@Valid @ModelAttribute("acntForm") Account form, BindingResult result, Model model, HttpSession session) throws SQLException {
        if (result.hasErrors()) {
            model.addAttribute("acntTypes", AccountType.toMapValue());
            model.addAttribute("users", userProfileService.selectAllUsers());
            model.addAttribute("validation", false);
            
            return "account/add";
        }
        
        form.setOwnerUserName(userProfileService.selectByKey(form.getOwnerOid()).getUserName());
        
        session.setAttribute("acntForm", form);
        
        return "account/confirmAdd";
    }
    
    
    @RequestMapping("/saveAdd")
    public String saveAdd(Model model, HttpSession session) throws SQLException {
        Account form = (Account) session.getAttribute("acntForm");
        
        BaseObject base = new BaseObject();
        base.setCreateTime(new Date());
        base.setCreateBy(SessionUtil.getInstance().getLoginUser(session).getUserName());
        
        form.setBaseObject(base);
        
        transactionService.createAccount(form);
        
        session.removeAttribute("acntForm");
        
        return "account/summary";
    }
    
    @RequestMapping("/view")
    public String view(@RequestParam("acntOid") BigDecimal acntOid, Model model) throws SQLException {
        Account form = accountService.selectByKey(acntOid);
        
        form.setOwnerUserName(userProfileService.selectByKey(form.getOwnerOid()).getUserName());
        
        model.addAttribute("acntForm", form);
        
        return "account/view";
    }
    
    @RequestMapping("/ajaxGetAllAccounts")
    @ResponseBody
    public List<Account> alaxGetAllAccounts() {
        
        try {
            List<Account> rlt =  accountService.queryAccounts();
            
            return rlt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
