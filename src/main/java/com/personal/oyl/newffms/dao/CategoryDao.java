package com.personal.oyl.newffms.dao;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.base.dao.BaseDao;
import com.personal.oyl.newffms.base.dao.DbActionDao;
import com.personal.oyl.newffms.pojo.Category;

public interface CategoryDao extends BaseDao<Category>, DbActionDao<Category> {

	public BigDecimal selectTotalBudgetByParent(BigDecimal parentOid) throws SQLException;
}
