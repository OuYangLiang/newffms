package com.personal.oyl.newffms.dao;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.base.dao.BaseDao;
import com.personal.oyl.newffms.base.dao.DbActionDao;
import com.personal.oyl.newffms.pojo.AccountIncoming;

public interface AccountIncomingDao extends BaseDao<AccountIncoming>, DbActionDao<AccountIncoming> {
    public AccountIncoming selectOneByAccount(BigDecimal acntOid) throws SQLException;
}
