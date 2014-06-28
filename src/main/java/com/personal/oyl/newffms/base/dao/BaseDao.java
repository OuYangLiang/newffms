package com.personal.oyl.newffms.base.dao;

import java.util.List;

import com.personal.oyl.newffms.pojo.BasePojo;

public interface BaseDao<T extends BasePojo> {
    public List<T> select(T param);
}
