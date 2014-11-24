package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.AccountConsumptionDao;
import com.personal.oyl.newffms.pojo.AccountConsumption;
import com.personal.oyl.newffms.pojo.key.AccountConsumptionKey;
import com.personal.oyl.newffms.service.AccountConsumptionService;

public class AccountConsumptionServiceImpl implements AccountConsumptionService {
    @Autowired
    private AccountConsumptionDao dao;

    @Override
    public void insert(AccountConsumption param) throws SQLException {
        dao.insert(param);
    }

    @Override
    public void updateByPrimaryKeySelective(AccountConsumption param) throws SQLException {
        // not available
    }

    @Override
    public void updateByPrimaryKey(AccountConsumption param) throws SQLException {
        // not available
    }

    @Override
    public void deleteByConsumption(BigDecimal cpnOid) throws SQLException {
        AccountConsumption param = new AccountConsumption();
        param.setCpnOid(cpnOid);
        
        dao.delete(param);
    }

    @Override
    public List<AccountConsumption> selectByConsumption(BigDecimal cpnOid) throws SQLException {
        AccountConsumption param = new AccountConsumption();
        param.setCpnOid(cpnOid);
        
        return dao.select(param);
    }

    @Override
	public AccountConsumption selectByKey(AccountConsumptionKey key)
			throws SQLException {
		AccountConsumption param = new AccountConsumption();
		param.setAcntOid(key.getAcntOid());
		param.setCpnOid(key.getCpnOid());
		
		List<AccountConsumption> list = dao.select(param);
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}

    @Override
	public void deleteByKey(AccountConsumptionKey key) throws SQLException {
		AccountConsumption param = new AccountConsumption();
		param.setAcntOid(key.getAcntOid());
		param.setCpnOid(key.getCpnOid());
		
		dao.delete(param);
	}

}
