package com.personal.oyl.newffms.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.AccountConsumptionDao;
import com.personal.oyl.newffms.pojo.AccountConsumption;
import com.personal.oyl.newffms.service.AccountConsumptionService;

public class AccountConsumptionServiceImpl implements AccountConsumptionService {
    @Autowired
    private AccountConsumptionDao dao;

    public void insert(AccountConsumption param) throws SQLException {
        dao.insert(param);
    }

    public void updateByPrimaryKeySelective(AccountConsumption param) throws SQLException {
        
    }

    public void updateByPrimaryKey(AccountConsumption param) throws SQLException {
        
    }

    public void delete(AccountConsumption param) throws SQLException {
        dao.delete(param);
    }

}
