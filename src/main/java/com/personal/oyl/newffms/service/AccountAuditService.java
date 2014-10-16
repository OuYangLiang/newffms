package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.AccountAudit;

public interface AccountAuditService extends BaseService<AccountAudit>, DbActionService<AccountAudit>,
        PaginatingService<AccountAudit> {
    public void deleteByConsumption(BigDecimal cpnOid) throws SQLException;

    public void deleteByIncoming(BigDecimal incomingOid) throws SQLException;

    public void deleteByAcnt(BigDecimal acntOid) throws SQLException;
}
