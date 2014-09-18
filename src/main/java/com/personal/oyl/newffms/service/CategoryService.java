package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.pojo.Category;

public interface CategoryService extends BaseService<Category>{
    
    public Category selectByKey(BigDecimal categoryOid) throws SQLException;
    
    public String selectFullDescByKey(BigDecimal categoryOid) throws SQLException;
}
