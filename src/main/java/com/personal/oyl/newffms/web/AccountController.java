package com.personal.oyl.newffms.web;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.personal.oyl.newffms.pojo.extension.Account;
import com.personal.oyl.newffms.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
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
