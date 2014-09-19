package com.personal.oyl.newffms.pojo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.ConsumptionItem;

public class ConsumptionFormValidator implements Validator {
    
    @Autowired
    private ConsumptionItemValidator itemValidator;
    @Autowired
    private AccountValidator accountValidator;

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
                errors.pushNestedPath("accounts[" + idx + "]");
                ValidationUtils.invokeValidator(accountValidator, acnt, errors);
            }
            finally {
                errors.popNestedPath();
            }
        }
    }

}
