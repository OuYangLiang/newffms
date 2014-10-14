package com.personal.oyl.newffms.pojo.validator;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.service.AccountService;

public class ConsumptionFormValidator implements Validator {
    private static final Logger log = LoggerFactory.getLogger(ConsumptionFormValidator.class);
    
    @Autowired
    private ConsumptionItemValidator itemValidator;
    @Autowired
    private AccountValidator accountValidator;
    
    @Autowired
    private AccountService accountService;

    public boolean supports(Class<?> clazz) {
        return ConsumptionForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        ConsumptionForm form = (ConsumptionForm) target;
        
        try {
            errors.pushNestedPath("consumption");
            ValidationUtils.invokeValidator(new ConsumptionValidator(), form.getConsumption(), errors);
        }
        finally {
            errors.popNestedPath();
        }
        
        if (null == form.getCpnItems() || form.getCpnItems().isEmpty()) {
            errors.reject(null, "没有消费项目啊，亲。");
            return;
        }
        
        if (null == form.getAccounts() || form.getAccounts().isEmpty() ) {
            errors.reject(null, "你想不付钱啊，亲。");
            return;
        }
        
        int idx = 0;
        for ( ConsumptionItem item : form.getCpnItems() ) {
            try {
                errors.pushNestedPath("cpnItems[" + idx + "]");
                ValidationUtils.invokeValidator(itemValidator, item, errors);
            }
            finally {
                errors.popNestedPath();
            }
            idx ++;
        }
        
        idx = 0;
        for ( Account acnt : form.getAccounts() ) {
            
            try {
                Account dbAcnt = accountService.selectByKey(acnt.getAcntOid());
                
                if (acnt.getPayment() != null) {
                    if (dbAcnt.getBalance().compareTo(acnt.getPayment()) < 0) {
                        errors.reject(null, "账户[ " + dbAcnt.getAcntHumanDesc() + " ]余额不足啊，亲。");
                    }
                }
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
            
        }
    }

}
