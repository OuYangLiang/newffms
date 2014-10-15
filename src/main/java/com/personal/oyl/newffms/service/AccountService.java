package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.Account;

public interface AccountService extends BaseService<Account>, DbActionService<Account>, PaginatingService<Account> {
    public List<Account> queryAccounts() throws SQLException;

    public Account selectByKey(BigDecimal acntOid) throws SQLException;

    public List<Account> queryAccountsByConsumption(BigDecimal cpnOid) throws SQLException;

    public Account queryAccountsByIncoming(BigDecimal incomingOid) throws SQLException;
    
    public void deleteByKey(BigDecimal acntOid) throws SQLException;
    
    public boolean isAccountSafeToRemove(BigDecimal acntOid) throws SQLException;
}
