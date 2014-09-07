package com.personal.oyl.newffms.dao;

import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.base.dao.BaseDao;
import com.personal.oyl.newffms.base.dao.DbActionDao;
import com.personal.oyl.newffms.pojo.Account;

public interface AccountDao extends BaseDao<Account>, DbActionDao<Account> {
    public List<com.personal.oyl.newffms.pojo.extension.Account> queryAccounts() throws SQLException;
}
