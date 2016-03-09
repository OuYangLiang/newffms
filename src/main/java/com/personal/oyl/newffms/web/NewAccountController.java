package com.personal.oyl.newffms.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.constants.AccountType;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.BootstrapTableJsonRlt;

@Controller
@RequestMapping("/new-account")
public class NewAccountController extends BaseController {
	
	private static final String SESSION_KEY_SEARCH_PARAM_ACCOUNT = "SESSION_KEY_SEARCH_PARAM_ACCOUNT";
	private static final Map<String, String> colMapping;
	
	static {
		colMapping = new HashMap<String, String>();
		colMapping.put("owner.userName", "OWNER_OID");
		colMapping.put("acntType", "ACNT_TYPE");
	}
	
	@RequestMapping("summary")
    public String summary(HttpServletRequest request, Model model, HttpSession session) throws SQLException {
        this.clearSearchParameter(request, session, SESSION_KEY_SEARCH_PARAM_ACCOUNT);
        
        //初始化查询条件。
        
        model.addAttribute("users", userProfileService.selectAllUsers());
        model.addAttribute("acntTypes", AccountType.toMapValue());
        
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

        return "new-account/summary";
    }
	
	@RequestMapping("search")
    @ResponseBody
    public String search(@RequestParam("ownerOid") BigDecimal ownerOid, 
    		@RequestParam("acntType") AccountType acntType,
    		HttpSession session) {
        //从页面接受查询参数，并放入session中。
        Account searchParam = new Account();
        searchParam.setOwnerOid(ownerOid);
        searchParam.setAcntType(acntType);
        
        session.setAttribute(SESSION_KEY_SEARCH_PARAM_ACCOUNT, searchParam);
        
        return "OK";
    }
	
	@RequestMapping("/listOfSummary")
    @ResponseBody
    public BootstrapTableJsonRlt<Account> listOfSummary(@RequestParam(value = "offset", required = true) int offset,
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "sort", required = true) String sort,
            @RequestParam(value = "order", required = true) String order, HttpSession session) throws SQLException {
		
		int sizePerPage = limit;
		int requestPage = offset / limit + 1;
		String sortField = colMapping.get(sort);
		String sortDir = order;
        
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
        
        return this.initBootstrapPaging(accountService, searchParam);
    }
}
