package com.personal.oyl.newffms.service;

import java.sql.SQLException;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.pojo.UserProfile;

public interface UserProfileService extends BaseService<UserProfile>{
    public UserProfile selectByLoginId(String loginId) throws SQLException;
}
