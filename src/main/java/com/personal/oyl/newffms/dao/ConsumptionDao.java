package com.personal.oyl.newffms.dao;

import com.personal.oyl.newffms.base.dao.BaseDao;
import com.personal.oyl.newffms.base.dao.DbActionDao;
import com.personal.oyl.newffms.base.dao.PaginatingDao;
import com.personal.oyl.newffms.pojo.Consumption;

public interface ConsumptionDao extends BaseDao<Consumption>, DbActionDao<Consumption>, PaginatingDao<Consumption> {

}
