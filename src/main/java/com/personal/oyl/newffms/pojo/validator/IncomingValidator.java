package com.personal.oyl.newffms.pojo.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.service.AccountService;

public class IncomingValidator implements Validator{
    
    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Incoming.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Incoming obj = (Incoming) target;
        
        if (null == obj.getOwnerOid()) {
            errors.reject(null, "谁的收入啊，亲。");
        }
        
        if (null == obj.getIncomingType()) {
            errors.reject(null, "收入类型呢，亲。");
        }
        
    }

}
