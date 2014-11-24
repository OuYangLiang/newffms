package com.personal.oyl.newffms.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.UserProfileDao;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.pojo.key.UserProfileKey;
import com.personal.oyl.newffms.service.UserProfileService;

public class UserProfileServiceImpl implements UserProfileService {
    
    @Autowired
    private UserProfileDao dao;

    @Override
    public UserProfile selectByLoginId(String loginId) throws SQLException {
        UserProfile param = new UserProfile();
        param.setLoginId(loginId.trim());
        
        List<UserProfile> list = dao.select(param);
        
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
            
        return null;
    }

    @Override
    public UserProfile selectByKey(UserProfileKey key) throws SQLException {
        UserProfile param = new UserProfile();
        param.setUserOid(key.getUserOid());
        
        List<UserProfile> list = dao.select(param);
        
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
            
        return null;
    }

    @Override
    public List<UserProfile> selectAllUsers() throws SQLException {
        return dao.select(null);
    }

    @Override
	public void insert(UserProfile param) throws SQLException {
		dao.insert(param);
	}

    @Override
	public void updateByPrimaryKeySelective(UserProfile param)
			throws SQLException {
		dao.updateByKeySelectively(param);
	}

    @Override
	public void updateByPrimaryKey(UserProfile param) throws SQLException {
		dao.updateByKey(param);
	}

    @Override
	public void deleteByKey(UserProfileKey key) throws SQLException {
		UserProfile param = new UserProfile();
        param.setUserOid(key.getUserOid());
        
        dao.delete(param);
	}

}
