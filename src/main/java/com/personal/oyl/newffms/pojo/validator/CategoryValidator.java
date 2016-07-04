package com.personal.oyl.newffms.pojo.validator;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.service.CategoryService;

public class CategoryValidator implements Validator {
	private static final Logger log = LoggerFactory.getLogger(CategoryValidator.class); 
	
	@Autowired
	private CategoryService categoryService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Category.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Category category = (Category) target;
		
		try {
			category.trimAllString();
			category.setAllEmptyStringToNull();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		if (null == category.getCategoryDesc() ) {
			errors.reject(null, "描述是什么，亲。");
			return;
		}
		
		if (null == category.getCategoryOid() && null == category.getMonthlyBudget()) {
			errors.reject(null, "月度预算是多少，亲。");
			return;
		}
		
		try {
			if (null == category.getCategoryOid()) {
				if (categoryService.isCategoryExist(category.getParentOid(), category.getCategoryDesc())) {
					errors.reject(null, "描述在当前层次已经存在了，亲。");
					return;
				}
			} else {
				Category oldObj = categoryService.selectByParentAndDesc(category.getParentOid(), category.getCategoryDesc());
				if (null != oldObj && !oldObj.getCategoryOid().equals(category.getCategoryOid())) {
					errors.reject(null, "描述在当前层次已经存在了，亲。");
					return;
				}
			}
			
			if (null == category.getCategoryOid() && null != category.getParentOid() 
					&& categoryService.isCategoryUsed(category.getParentOid())) {
				errors.reject(null, "父类别已经被使用过了，不能再作为父类了，亲。");
				return;
			}
			
			
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}

}
