package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.pojo.Category;

public interface CategoryService extends BaseService<Category>,
		DbActionService<Category> {

	public Category selectByKey(BigDecimal categoryOid) throws SQLException;

	public List<Category> selectByLevel(Integer categoryLevel)
			throws SQLException;

	public String selectFullDescByKey(BigDecimal categoryOid)
			throws SQLException;

	public List<Category> selectAllCategories() throws SQLException;

	public List<Category> selectAllCategoriesWithExclusion(
			Set<BigDecimal> excludingRootCategoryOids) throws SQLException;
}
