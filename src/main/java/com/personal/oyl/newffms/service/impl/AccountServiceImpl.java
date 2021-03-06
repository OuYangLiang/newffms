package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.AccountConsumptionDao;
import com.personal.oyl.newffms.dao.AccountDao;
import com.personal.oyl.newffms.dao.AccountIncomingDao;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.key.AccountKey;
import com.personal.oyl.newffms.service.AccountService;

public class AccountServiceImpl implements AccountService {
    
    @Autowired
    private AccountDao dao;
    @Autowired
    private AccountIncomingDao accountIncomingDao;
    @Autowired
    private AccountConsumptionDao accountConsumptionDao;

    @Override
    public List<Account> queryAccounts() throws SQLException {
        return dao.queryAccounts();
    }

    @Override
    public void insert(Account param) throws SQLException {
        dao.insert(param);
    }

    @Override
    public void updateByPrimaryKeySelective(Account param) throws SQLException {
        dao.updateByKeySelectively(param);
    }

    @Override
    public void updateByPrimaryKey(Account param) throws SQLException {
        dao.updateByKey(param);
    }

    @Override
    public Account selectByKey(AccountKey key) throws SQLException {
        Account param = new Account();
        param.setAcntOid(key.getAcntOid());
        
        List<Account> list = dao.select(param);
        
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }

    @Override
    public List<Account> queryAccountsByConsumption(BigDecimal cpnOid) throws SQLException {
        return dao.queryAccountsByConsumption(cpnOid);
    }

    @Override
    public Account queryAccountsByIncoming(BigDecimal incomingOid) throws SQLException {
        return dao.queryAccountsByIncoming(incomingOid);
    }

    @Override
    public int getCountOfSummary(Account param) throws SQLException {
        return dao.getCountOfSummary(param);
    }

    @Override
    public List<Account> getListOfSummary(Account param) throws SQLException {
        return dao.getListOfSummary(param);
    }

    @Override
    public void deleteByKey(AccountKey key) throws SQLException {
        Account param = new Account();
        param.setAcntOid(key.getAcntOid());
        
        dao.delete(param);
    }

    @Override
    public boolean isAccountSafeToRemove(BigDecimal acntOid) throws SQLException {
        Object obj = accountConsumptionDao.selectOneByAccount(acntOid);
        
        if (null != obj) {
            return false;
        }
        
        return accountIncomingDao.selectOneByAccount(acntOid) == null;
        
    }

}
