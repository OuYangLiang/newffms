package com.personal.oyl.newffms.pojo.validator;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.service.AccountService;

public class AccountValidator implements Validator {
    private static final Logger log = LoggerFactory.getLogger(AccountValidator.class);
    
    @Autowired
    private AccountService accountService;

    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        Account acnt = (Account) target;
        
        try {
            Account dbAcnt = accountService.selectByKey(acnt.getAcntOid());
            
            if (acnt.getPayment() != null) {
                if (dbAcnt.getBalance().compareTo(acnt.getPayment()) < 0) {
                    errors.rejectValue("payment", null, "账户[ " + dbAcnt.getAcntHumanDesc() + " ]余额不足啊，亲。");
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

}
