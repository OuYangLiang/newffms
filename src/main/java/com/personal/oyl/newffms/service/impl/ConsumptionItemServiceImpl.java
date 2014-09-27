package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.ConsumptionItemDao;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.service.ConsumptionItemService;

public class ConsumptionItemServiceImpl implements ConsumptionItemService {
    @Autowired
    private ConsumptionItemDao dao;

    public List<ConsumptionItem> select(ConsumptionItem param) throws SQLException {
        return dao.select(param);
    }

    public void insert(ConsumptionItem param) throws SQLException {
        dao.insert(param);
    }

    public void updateByPrimaryKeySelective(ConsumptionItem param) throws SQLException {
        dao.updateByKeySelectively(param);
    }

    public void updateByPrimaryKey(ConsumptionItem param) throws SQLException {
        dao.updateByKey(param);
    }

    public void delete(ConsumptionItem param) throws SQLException {
        dao.delete(param);
    }

    public List<ConsumptionItem> queryConsumptionItemByCpn(BigDecimal cpnOid) throws SQLException {
        return dao.queryConsumptionItemByCpn(cpnOid);
    }

    public void deleteByConsumption(BigDecimal cpnOid) throws SQLException {
        ConsumptionItem param = new ConsumptionItem();
        param.setCpnOid(cpnOid);
        
        this.delete(param);
    }

}
