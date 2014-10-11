package com.personal.oyl.newffms.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.base.dao.BaseDao;
import com.personal.oyl.newffms.base.dao.DbActionDao;
import com.personal.oyl.newffms.pojo.Account;

public interface AccountDao extends BaseDao<Account>, DbActionDao<Account> {
    public List<Account> queryAccounts() throws SQLException;
    
    public List<Account> queryAccountsByConsumption(BigDecimal cpnOid) throws SQLException;
    
    public Account queryAccountsByIncoming(BigDecimal incomingOid) throws SQLException;
}
