package com.personal.oyl.newffms.base.service;

import java.sql.SQLException;

import com.personal.oyl.newffms.pojo.BasePojo;

public interface DbActionService<T extends BasePojo> {
    public void insert(T param) throws SQLException;
    
    public void updateByPrimaryKeySelective(T param) throws SQLException;
    
    public void updateByPrimaryKey(T param) throws SQLException;
    
    public void delete(T param) throws SQLException;
}
