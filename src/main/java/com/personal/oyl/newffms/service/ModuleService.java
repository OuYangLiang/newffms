package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.pojo.Module;

public interface ModuleService {
	List<Module> selectMenusByUser(BigDecimal userOid) throws SQLException;
}
