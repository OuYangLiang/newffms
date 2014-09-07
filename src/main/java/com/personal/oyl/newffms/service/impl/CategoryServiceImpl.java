package com.personal.oyl.newffms.service.impl;

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

}
