package com.personal.oyl.newffms.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.ConsumptionDao;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.key.ConsumptionKey;
import com.personal.oyl.newffms.report.PersonalConsumption;
import com.personal.oyl.newffms.service.ConsumptionService;

public class ConsumptionServiceImpl implements ConsumptionService {
    
    @Autowired
    private ConsumptionDao dao;

    @Override
    public void insert(Consumption param) throws SQLException {
        dao.insert(param);
    }

    @Override
    public void updateByPrimaryKeySelective(Consumption param) throws SQLException {
        dao.updateByKeySelectively(param);
    }

    @Override
    public void updateByPrimaryKey(Consumption param) throws SQLException {
        dao.updateByKey(param);
    }

    @Override
    public int getCountOfSummary(Consumption param) throws SQLException {
        return dao.getCountOfSummary(param);
    }

    @Override
    public List<Consumption> getListOfSummary(Consumption param) throws SQLException {
        return dao.getListOfSummary(param);
    }

    @Override
    public Consumption selectByKey(ConsumptionKey key) throws SQLException {
        Consumption param = new Consumption();
        param.setCpnOid(key.getCpnOid());
        
        List<Consumption> list = dao.select(param);
        
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }

    @Override
    public void deleteByKey(ConsumptionKey key) throws SQLException {
        Consumption param = new Consumption();
        param.setCpnOid(key.getCpnOid());
        
        dao.delete(param);
    }

    @Override
    public List<PersonalConsumption> queryPersonalConsumption(Date start, Date end) throws SQLException {
        Map<String, Date> param = new HashMap<String, Date>();
        param.put("start", start);
        param.put("end", end);
        
        return dao.queryPersonalConsumption(param);
    }

}
