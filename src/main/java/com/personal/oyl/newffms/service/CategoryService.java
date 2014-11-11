package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.pojo.key.CategoryKey;

public interface CategoryService extends BaseService<Category, CategoryKey>,
		DbActionService<Category, CategoryKey> {

	public List<Category> selectByLevel(Integer categoryLevel)
			throws SQLException;
	
	public String selectFullDescByKey(BigDecimal categoryOid)
			throws SQLException;

	public List<Category> selectAllCategories() throws SQLException;

	public List<Category> selectAllCategoriesWithExclusion(
			Set<BigDecimal> excludingRootCategoryOids) throws SQLException;
	
	public BigDecimal selectTotalBudgetByParent(BigDecimal parentOid) throws SQLException;
	
	public boolean isCategoryUsedByIncoming(BigDecimal categoryOid) throws SQLException;
	
	public Category selectByParentAndDesc(BigDecimal parentOid, String categoryDesc) throws SQLException;
	
	public List<Category> selectByParent(BigDecimal parentOid) throws SQLException;
	
	public boolean isCategoryExist(BigDecimal parentOid, String categoryDesc) throws SQLException;
	
}
