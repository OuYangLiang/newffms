package com.personal.oyl.newffms.pojo.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.pojo.ConsumptionItem;

public class ConsumptionItemValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return ConsumptionItem.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "ownerOid", null, "是谁在消费啊，亲。");
    }

}
