package com.personal.oyl.newffms.pojo.validator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.service.AccountService;

public class IncomingValidator implements Validator{
    private static final Logger log = LoggerFactory.getLogger(IncomingValidator.class);
    
    @Autowired
    private AccountService accountService;

    public boolean supports(Class<?> clazz) {
        return Incoming.class.equals(clazz);
    }

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
