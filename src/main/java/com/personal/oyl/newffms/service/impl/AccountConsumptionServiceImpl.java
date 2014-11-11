package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

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
        // not available
    }

    public void updateByPrimaryKey(AccountConsumption param) throws SQLException {
        // not available
    }

    public void deleteByConsumption(BigDecimal cpnOid) throws SQLException {
        AccountConsumption param = new AccountConsumption();
        param.setCpnOid(cpnOid);
        
        dao.delete(param);
    }

    public List<AccountConsumption> selectByConsumption(BigDecimal cpnOid) throws SQLException {
        AccountConsumption param = new AccountConsumption();
        param.setCpnOid(cpnOid);
        
        return dao.select(param);
    }

}
