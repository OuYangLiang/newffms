package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.report.PersonalConsumption;

public interface ConsumptionService extends BaseService<Consumption>, DbActionService<Consumption>,
        PaginatingService<Consumption> {
    
    public Consumption selectByKey(BigDecimal cpnOid) throws SQLException;
    
    public void deleteByKey(BigDecimal cpnOid) throws SQLException;

    public List<PersonalConsumption> queryPersonalConsumption(Date start, Date end) throws SQLException;
}
