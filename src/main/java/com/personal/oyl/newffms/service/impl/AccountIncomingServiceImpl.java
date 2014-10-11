package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.AccountIncomingDao;
import com.personal.oyl.newffms.pojo.AccountIncoming;
import com.personal.oyl.newffms.service.AccountIncomingService;

public class AccountIncomingServiceImpl implements AccountIncomingService {
    @Autowired
    private AccountIncomingDao dao;

    public List<AccountIncoming> select(AccountIncoming param) throws SQLException {
        return dao.select(param);
    }

    public void insert(AccountIncoming param) throws SQLException {
        dao.insert(param);
    }

    public void updateByPrimaryKeySelective(AccountIncoming param) throws SQLException {
        //N/A
    }

    public void updateByPrimaryKey(AccountIncoming param) throws SQLException {
        //N/A
    }

    public void delete(AccountIncoming param) throws SQLException {
        dao.delete(param);
    }

    public AccountIncoming selectByIncoming(BigDecimal incomingOid) throws SQLException {
        AccountIncoming param = new AccountIncoming();
        param.setIncomingOid(incomingOid);
        
        List<AccountIncoming> list = this.select(param);
        
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }

    public void deleteByIncoming(BigDecimal incomingOid) throws SQLException {
        AccountIncoming param = new AccountIncoming();
        param.setIncomingOid(incomingOid);
        
        dao.delete(param);
    }

}
