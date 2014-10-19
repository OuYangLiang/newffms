package com.personal.oyl.newffms.pojo.validator;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.service.UserProfileService;

public class ProfileValidator implements Validator {
	
	private static final String PHONE_REGEX = "^[\\d]{11}$";
	private static final String EMAIL_REGEX = "^\\w+@\\w+\\.\\w+$";
	private static final String ID_REGEX = "^[A-Za-z_]\\w{0,9}$";
	
	private static final Logger log = LoggerFactory.getLogger(ProfileValidator.class);
	
	@Autowired
	private UserProfileService userProfileService;

	public boolean supports(Class<?> clazz) {
		return UserProfile.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		UserProfile user = (UserProfile) target;
		
		if (null == user.getUserName()) {
			errors.reject(null, "姓名是什么，亲。");
		}
		
		if (null == user.getGender()) {
			errors.reject(null, "性别是什么，亲。");
		}
		
		if (null == user.getPhone()) {
			errors.reject(null, "手机是多少，亲。");
		} else if (null != user.getPhone()
				&& !user.getPhone().matches(PHONE_REGEX)) {
			errors.reject(null, "手机格式不对吧，亲。");
		}
		
		if (null == user.getEmail()) {
			errors.reject(null, "邮箱是多少，亲。");
		} else if (null != user.getEmail()
				&& !user.getEmail().matches(EMAIL_REGEX)) {
			errors.reject(null, "邮箱格式不对吧，亲。");
		}
		
		if (null == user.getLoginId()) {
			errors.reject(null, "用户名是什么，亲。");
		}
		
		if (null != user.getLoginId()) {
			
			if (!user.getLoginId().matches(ID_REGEX)) {
				errors.reject(null, "用户名只能是字母、数字或下划线，且不能以数字开头，亲。");
			} else {
				try {
					UserProfile oldObj = userProfileService.selectByLoginId(user.getLoginId());
					
					if (null != oldObj && oldObj.getUserOid().compareTo(user.getUserOid()) != 0) {
						errors.reject(null, "用户名已经存在，亲。");
					}
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
			
	}

}
