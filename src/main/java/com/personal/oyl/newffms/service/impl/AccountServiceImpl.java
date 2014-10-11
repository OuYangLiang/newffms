package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.AccountDao;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.service.AccountService;

public class AccountServiceImpl implements AccountService {
    
    @Autowired
    private AccountDao dao;

    public List<Account> queryAccounts() throws SQLException {
        return dao.queryAccounts();
    }

    public List<Account> select(Account param) throws SQLException {
        return dao.select(param);
    }

    public void insert(Account param) throws SQLException {
        dao.insert(param);
    }

    public void updateByPrimaryKeySelective(Account param) throws SQLException {
        dao.updateByKeySelectively(param);
    }

    public void updateByPrimaryKey(Account param) throws SQLException {
        dao.updateByKey(param);
    }

    public void delete(Account param) throws SQLException {
        dao.delete(param);
    }

    public Account selectByKey(BigDecimal acntOid) throws SQLException {
        Account param = new Account();
        param.setAcntOid(acntOid);
        
        List<Account> list = this.select(param);
        
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }

    public List<Account> queryAccountsByConsumption(BigDecimal cpnOid) throws SQLException {
        return dao.queryAccountsByConsumption(cpnOid);
    }

    public Account queryAccountsByIncoming(BigDecimal incomingOid) throws SQLException {
        return dao.queryAccountsByIncoming(incomingOid);
    }

}
