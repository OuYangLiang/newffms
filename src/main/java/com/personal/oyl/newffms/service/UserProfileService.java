package com.personal.oyl.newffms.service;

import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.pojo.key.UserProfileKey;

public interface UserProfileService extends
		BaseService<UserProfile, UserProfileKey>,
		DbActionService<UserProfile, UserProfileKey> {
	public UserProfile selectByLoginId(String loginId) throws SQLException;

	public List<UserProfile> selectAllUsers() throws SQLException;
}
