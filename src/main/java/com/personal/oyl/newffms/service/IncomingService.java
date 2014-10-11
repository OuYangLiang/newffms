package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.Incoming;

public interface IncomingService extends BaseService<Incoming>, DbActionService<Incoming>, PaginatingService<Incoming> {
    
    public Incoming selectByKey(BigDecimal incomingOid) throws SQLException;

}
