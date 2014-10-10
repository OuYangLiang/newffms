package com.personal.oyl.newffms.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.IncomingDao;
import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.service.IncomingService;

public class IncomingServiceImpl implements IncomingService {
    @Autowired
    private IncomingDao incomingDao;

    public List<Incoming> select(Incoming param) throws SQLException {
        return incomingDao.select(param);
    }

    public void insert(Incoming param) throws SQLException {
        incomingDao.insert(param);
    }

    public void updateByPrimaryKeySelective(Incoming param) throws SQLException {
        incomingDao.updateByKeySelectively(param);
    }

    public void updateByPrimaryKey(Incoming param) throws SQLException {
        incomingDao.updateByKey(param);
    }

    public void delete(Incoming param) throws SQLException {
        incomingDao.delete(param);
    }

    public int getCountOfSummary(Incoming param) throws SQLException {
        return incomingDao.getCountOfSummary(param);
    }

    public List<Incoming> getListOfSummary(Incoming param) throws SQLException {
        return incomingDao.getListOfSummary(param);
    }

}
