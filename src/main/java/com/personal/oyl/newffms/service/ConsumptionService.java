package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.Consumption;

public interface ConsumptionService extends BaseService<Consumption>, DbActionService<Consumption>,
        PaginatingService<Consumption> {
    
    public Consumption selectByKey(BigDecimal cpnOid) throws SQLException;

}
