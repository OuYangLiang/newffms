package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.UserProfileDao;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.service.UserProfileService;

public class UserProfileServiceImpl implements UserProfileService {
    
    @Autowired
    private UserProfileDao dao;

    public List<UserProfile> select(UserProfile param) throws SQLException {
        return dao.select(param);
    }

    public UserProfile selectByLoginId(String loginId) throws SQLException {
        UserProfile param = new UserProfile();
        param.setLoginId(loginId.trim());
        
        List<UserProfile> list = this.select(param);
        
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
            
        return null;
    }

    public UserProfile selectByKey(BigDecimal userOid) throws SQLException {
        UserProfile param = new UserProfile();
        param.setUserOid(userOid);
        
        List<UserProfile> list = this.select(param);
        
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
            
        return null;
    }

    public List<UserProfile> selectAllUsers() throws SQLException {
        return this.select(null);
    }

}
