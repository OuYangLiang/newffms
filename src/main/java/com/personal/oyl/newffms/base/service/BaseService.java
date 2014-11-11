package com.personal.oyl.newffms.base.service;

import java.sql.SQLException;

import com.personal.oyl.newffms.pojo.BasePojo;
import com.personal.oyl.newffms.pojo.key.Key;

public interface BaseService<T extends BasePojo, K extends Key> {
	public T selectByKey(K key) throws SQLException;
}
