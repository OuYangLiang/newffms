package com.personal.oyl.newffms.dao;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.base.dao.BaseDao;
import com.personal.oyl.newffms.base.dao.DbActionDao;
import com.personal.oyl.newffms.pojo.AccountConsumption;

public interface AccountConsumptionDao extends BaseDao<AccountConsumption>, DbActionDao<AccountConsumption> {
    public AccountConsumption selectOneByAccount(BigDecimal acntOid) throws SQLException;
}
