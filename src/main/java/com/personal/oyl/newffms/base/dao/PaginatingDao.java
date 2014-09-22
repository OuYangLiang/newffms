package com.personal.oyl.newffms.base.dao;

import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.pojo.BasePojo;

public interface PaginatingDao<T extends BasePojo> {
    
    public int getCountOfSummary(T param) throws SQLException;
    
    public List<T> getListOfSummary(T param) throws SQLException;

}
