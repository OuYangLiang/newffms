package com.personal.oyl.newffms.base.dao;

import com.personal.oyl.newffms.pojo.BasePojo;

public interface DbActionDao<T extends BasePojo> {
    public int delete(T param);

    public int insert(T param);
    
    public int updateByKeySelectively(T param);

    public int updateByKey(T param);
}
