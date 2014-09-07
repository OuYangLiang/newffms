package com.personal.oyl.newffms.base.dao;

import java.sql.SQLException;

import com.personal.oyl.newffms.pojo.BasePojo;

public interface DbActionDao<T extends BasePojo> {
    public int delete(T param) throws SQLException;

    public int insert(T param) throws SQLException;
    
    public int updateByKeySelectively(T param) throws SQLException;

    public int updateByKey(T param) throws SQLException;
}
