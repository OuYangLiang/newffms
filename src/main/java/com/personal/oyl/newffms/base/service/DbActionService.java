package com.personal.oyl.newffms.base.service;

import java.sql.SQLException;

import com.personal.oyl.newffms.pojo.BasePojo;
import com.personal.oyl.newffms.pojo.key.Key;

public interface DbActionService<T extends BasePojo, K extends Key> {
    public void insert(T param) throws SQLException;
    
    public void updateByPrimaryKeySelective(T param) throws SQLException;
    
    public void updateByPrimaryKey(T param) throws SQLException;
    
    public void deleteByKey(K key) throws SQLException;
}
