package com.personal.oyl.newffms.pojo.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.pojo.Consumption;

public class ConsumptionValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return Consumption.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "cpnType", null, "消费类型不可以为空哦，亲。");
    }

}
