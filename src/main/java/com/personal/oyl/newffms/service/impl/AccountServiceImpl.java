package com.personal.oyl.newffms.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.AccountDao;
import com.personal.oyl.newffms.service.AccountService;

public class AccountServiceImpl implements AccountService {
    
    @Autowired
    private AccountDao dao;

    public List<com.personal.oyl.newffms.pojo.extension.Account> queryAccounts() throws SQLException {
        return dao.queryAccounts();
    }

}
