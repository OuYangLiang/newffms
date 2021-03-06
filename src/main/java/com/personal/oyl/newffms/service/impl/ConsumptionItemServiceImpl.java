package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.ConsumptionItemDao;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.pojo.key.ConsumptionItemKey;
import com.personal.oyl.newffms.service.ConsumptionItemService;

public class ConsumptionItemServiceImpl implements ConsumptionItemService {
    @Autowired
    private ConsumptionItemDao dao;

    @Override
    public void insert(ConsumptionItem param) throws SQLException {
        dao.insert(param);
    }

    @Override
    public void updateByPrimaryKeySelective(ConsumptionItem param) throws SQLException {
        dao.updateByKeySelectively(param);
    }

    @Override
    public void updateByPrimaryKey(ConsumptionItem param) throws SQLException {
        dao.updateByKey(param);
    }

    @Override
    public List<ConsumptionItem> queryConsumptionItemByCpn(BigDecimal cpnOid) throws SQLException {
        return dao.queryConsumptionItemByCpn(cpnOid);
    }

    @Override
    public void deleteByConsumption(BigDecimal cpnOid) throws SQLException {
        ConsumptionItem param = new ConsumptionItem();
        param.setCpnOid(cpnOid);
        
        dao.delete(param);
    }

    @Override
	public int getCountOfSummary(ConsumptionItem param) throws SQLException {
		return dao.getCountOfSummary(param);
	}

    @Override
	public List<ConsumptionItem> getListOfSummary(ConsumptionItem param)
			throws SQLException {
		return dao.getListOfSummary(param);
	}

    @Override
	public ConsumptionItem selectByKey(ConsumptionItemKey key)
			throws SQLException {
		ConsumptionItem param = new ConsumptionItem();
		param.setItemOid(key.getItemOid());
		
		List<ConsumptionItem> list = dao.select(param);
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}

    @Override
	public void deleteByKey(ConsumptionItemKey key) throws SQLException {
		ConsumptionItem param = new ConsumptionItem();
		param.setItemOid(key.getItemOid());
		
		dao.delete(param);
	}

}
