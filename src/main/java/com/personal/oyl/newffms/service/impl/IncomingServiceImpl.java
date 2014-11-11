package com.personal.oyl.newffms.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.IncomingDao;
import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.pojo.key.IncomingKey;
import com.personal.oyl.newffms.service.IncomingService;

public class IncomingServiceImpl implements IncomingService {
    @Autowired
    private IncomingDao dao;

    public void insert(Incoming param) throws SQLException {
        dao.insert(param);
    }

    public void updateByPrimaryKeySelective(Incoming param) throws SQLException {
        dao.updateByKeySelectively(param);
    }

    public void updateByPrimaryKey(Incoming param) throws SQLException {
        dao.updateByKey(param);
    }

    public int getCountOfSummary(Incoming param) throws SQLException {
        return dao.getCountOfSummary(param);
    }

    public List<Incoming> getListOfSummary(Incoming param) throws SQLException {
        return dao.getListOfSummary(param);
    }

    public Incoming selectByKey(IncomingKey key) throws SQLException {
        Incoming param = new Incoming();
        param.setIncomingOid(key.getIncomingOid());
        
        List<Incoming> list = dao.select(param);
        
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }

    public void deleteByKey(IncomingKey key) throws SQLException {
        Incoming param = new Incoming();
        param.setIncomingOid(key.getIncomingOid());
        
        dao.delete(param);
    }

    public List<Incoming> selectByIncomingDateRange(Date incomingDateFrom, Date incomingDateTo) throws SQLException {
        Incoming param = new Incoming();
        param.setIncomingDateFrom(incomingDateFrom);
        param.setIncomingDateTo(incomingDateTo);
        
        return dao.select(param);
    }

}
