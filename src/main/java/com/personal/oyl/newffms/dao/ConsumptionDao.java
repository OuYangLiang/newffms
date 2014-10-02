package com.personal.oyl.newffms.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.personal.oyl.newffms.base.dao.BaseDao;
import com.personal.oyl.newffms.base.dao.DbActionDao;
import com.personal.oyl.newffms.base.dao.PaginatingDao;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.report.PersonalConsumption;

public interface ConsumptionDao extends BaseDao<Consumption>, DbActionDao<Consumption>, PaginatingDao<Consumption> {
    public List<PersonalConsumption> queryPersonalConsumption(Map<String, Date> param) throws SQLException;
}
