package com.personal.oyl.newffms.base.service;

import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.pojo.BasePojo;

public interface BaseService<T extends BasePojo> {
    public List<T> select(T param) throws SQLException;
}
