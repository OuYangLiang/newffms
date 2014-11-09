package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.AccountAuditDao;
import com.personal.oyl.newffms.pojo.AccountAudit;
import com.personal.oyl.newffms.service.AccountAuditService;

public class AccountAuditServiceImpl implements AccountAuditService {
    @Autowired
    private AccountAuditDao dao;

    public List<AccountAudit> select(AccountAudit param) throws SQLException {
        return dao.select(param);
    }

    public void insert(AccountAudit param) throws SQLException {
        dao.insert(param);
    }

    public void updateByPrimaryKeySelective(AccountAudit param) throws SQLException {
        dao.updateByKeySelectively(param);
    }

    public void updateByPrimaryKey(AccountAudit param) throws SQLException {
        dao.updateByKey(param);
    }

    public void deleteByConsumption(BigDecimal cpnOid) throws SQLException {
        AccountAudit param = new AccountAudit();
        param.setCpnOid(cpnOid);
        
        dao.delete(param);
    }

    public void deleteByIncoming(BigDecimal incomingOid) throws SQLException {
        AccountAudit param = new AccountAudit();
        param.setIncomingOid(incomingOid);
        
        dao.delete(param);
    }

    public void deleteByAcnt(BigDecimal acntOid) throws SQLException {
        AccountAudit param = new AccountAudit();
        param.setAcntOid(acntOid);
        
        dao.delete(param);
    }

    public int getCountOfSummary(AccountAudit param) throws SQLException {
        return dao.getCountOfSummary(param);
    }

    public List<AccountAudit> getListOfSummary(AccountAudit param) throws SQLException {
        return dao.getListOfSummary(param);
    }

}
