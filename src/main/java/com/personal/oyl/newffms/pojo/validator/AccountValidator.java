package com.personal.oyl.newffms.pojo.validator;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.constants.AccountType;
import com.personal.oyl.newffms.pojo.Account;

public class AccountValidator implements Validator {
    

    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        Account acnt = (Account) target;
        
        if (null == acnt.getOwnerOid()) {
            errors.reject(null, "账户所有人是谁啊，亲。");
        }
        
        if (null == acnt.getAcntType()) {
            errors.reject(null, "账户类型是什么，亲。");
        }
        
        if (AccountType.Creditcard.equals(acnt.getAcntType())) {
            if (null == acnt.getQuota()) {
                errors.reject(null, "信用卡限定额度是多少，亲。");
            } else if (acnt.getQuota().compareTo(BigDecimal.ZERO) <= 0) {
                errors.reject(null, "信用卡限定额度没问题？你确定吗，亲。");
            }
            
            if (null == acnt.getDebt()) {
                errors.reject(null, "信用卡欠款额度是多少，亲。");
            } else if (acnt.getDebt().compareTo(BigDecimal.ZERO) < 0) {
                errors.reject(null, "信用卡欠款额度是负数，你确定吗，亲。");
            }
        }
        
    }

}
