package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.CategoryDao;
import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryDao dao;

    public List<Category> select(Category param) throws SQLException {
        return dao.select(param);
    }
    
    public Category selectByKey(BigDecimal categoryOid) throws SQLException {
        Category param = new Category();
        param.setCategoryOid(categoryOid);
        
        List<Category> list = this.select(param);
        
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }

    
    public String selectFullDescByKey(BigDecimal categoryOid) throws SQLException {
        StringBuffer rlt = new StringBuffer();
        
        Category param = this.selectByKey(categoryOid);
        rlt.append(param.getCategoryDesc());
        
        while (param.getParentOid() != null) {
            param = this.selectByKey(param.getParentOid());
            rlt.insert(0, "-->").insert(0, param.getCategoryDesc());
            param = this.selectByKey(param.getParentOid());
        }
        
        return rlt.toString();
    }

}
