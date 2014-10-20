package com.personal.oyl.newffms.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.personal.oyl.newffms.base.dao.BaseDao;
import com.personal.oyl.newffms.base.dao.DbActionDao;
import com.personal.oyl.newffms.pojo.Category;

public interface CategoryDao extends BaseDao<Category>, DbActionDao<Category> {

	public BigDecimal selectTotalBudgetByParent(BigDecimal parentOid) throws SQLException;
	
	public Category selectByParentAndDesc(Map<String, Object> param) throws SQLException;
	
	public List<Category> selectByParent(Map<String, Object> param) throws SQLException;
}
