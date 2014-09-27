package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.pojo.AccountConsumption;

public interface AccountConsumptionService extends DbActionService<AccountConsumption> {
    public void deleteByConsumption(BigDecimal cpnOid) throws SQLException;
}
