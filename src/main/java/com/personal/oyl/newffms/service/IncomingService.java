package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.Incoming;

public interface IncomingService extends BaseService<Incoming>, DbActionService<Incoming>, PaginatingService<Incoming> {
    
    public Incoming selectByKey(BigDecimal incomingOid) throws SQLException;
    
    public void deleteByKey(BigDecimal incomingOid) throws SQLException;
    
    public List<Incoming> selectByIncomingDateRange(Date incomingDateFrom, Date incomingDateTo) throws SQLException;

}
