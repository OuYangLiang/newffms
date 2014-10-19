package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.pojo.UserProfile;

public interface UserProfileService extends BaseService<UserProfile>, DbActionService<UserProfile>{
    public UserProfile selectByLoginId(String loginId) throws SQLException;
    
    public UserProfile selectByKey(BigDecimal userOid) throws SQLException;
    
    public List<UserProfile> selectAllUsers() throws SQLException;
}
