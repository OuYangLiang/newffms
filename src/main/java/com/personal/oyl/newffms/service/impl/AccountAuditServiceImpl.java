package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.AccountAuditDao;
import com.personal.oyl.newffms.pojo.AccountAudit;
import com.personal.oyl.newffms.pojo.key.AccountAuditKey;
import com.personal.oyl.newffms.service.AccountAuditService;

public class AccountAuditServiceImpl implements AccountAuditService {
    @Autowired
    private AccountAuditDao dao;

    @Override
    public void insert(AccountAudit param) throws SQLException {
        dao.insert(param);
    }

    @Override
    public void updateByPrimaryKeySelective(AccountAudit param) throws SQLException {
        dao.updateByKeySelectively(param);
    }

    @Override
    public void updateByPrimaryKey(AccountAudit param) throws SQLException {
        dao.updateByKey(param);
    }

    @Override
    public void deleteByConsumption(BigDecimal cpnOid) throws SQLException {
        AccountAudit param = new AccountAudit();
        param.setCpnOid(cpnOid);
        
        dao.delete(param);
    }

    @Override
    public void deleteByIncoming(BigDecimal incomingOid) throws SQLException {
        AccountAudit param = new AccountAudit();
        param.setIncomingOid(incomingOid);
        
        dao.delete(param);
    }

    @Override
    public void deleteByAcnt(BigDecimal acntOid) throws SQLException {
        AccountAudit param = new AccountAudit();
        param.setAcntOid(acntOid);
        
        dao.delete(param);
    }

    @Override
    public int getCountOfSummary(AccountAudit param) throws SQLException {
        return dao.getCountOfSummary(param);
    }

    @Override
    public List<AccountAudit> getListOfSummary(AccountAudit param) throws SQLException {
        return dao.getListOfSummary(param);
    }

    @Override
	public AccountAudit selectByKey(AccountAuditKey key) throws SQLException {
		AccountAudit param = new AccountAudit();
		param.setAdtOid(key.getAdtOid());
		
		List<AccountAudit> list = dao.select(param);
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}

    @Override
	public void deleteByKey(AccountAuditKey key) throws SQLException {
		AccountAudit param = new AccountAudit();
		param.setAdtOid(key.getAdtOid());
		
		dao.select(param);
	}

}
