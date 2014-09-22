package com.personal.oyl.newffms.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.ConsumptionDao;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.service.ConsumptionService;

public class ConsumptionServiceImpl implements ConsumptionService {
    
    @Autowired
    private ConsumptionDao dao;

    public List<Consumption> select(Consumption param) throws SQLException {
        return dao.select(param);
    }

    public void insert(Consumption param) throws SQLException {
        dao.insert(param);
    }

    public void updateByPrimaryKeySelective(Consumption param) throws SQLException {
        dao.updateByKeySelectively(param);
    }

    public void updateByPrimaryKey(Consumption param) throws SQLException {
        dao.updateByKey(param);
    }

    public void delete(Consumption param) throws SQLException {
        dao.delete(param);
    }

    public int getCountOfSummary(Consumption param) throws SQLException {
        return dao.getCountOfSummary(param);
    }

    public List<Consumption> getListOfSummary(Consumption param) throws SQLException {
        return dao.getListOfSummary(param);
    }

}
