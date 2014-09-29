package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.pojo.AccountConsumption;

public interface AccountConsumptionService extends BaseService<AccountConsumption>, DbActionService<AccountConsumption> {
    public void deleteByConsumption(BigDecimal cpnOid) throws SQLException;

    public List<AccountConsumption> selectByConsumption(BigDecimal cpnOid) throws SQLException;
}