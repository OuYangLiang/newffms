package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.ModuleDao;
import com.personal.oyl.newffms.pojo.Module;
import com.personal.oyl.newffms.service.ModuleService;

public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private ModuleDao dao;

	@Override
	public List<Module> selectMenusByUser(BigDecimal userOid)
			throws SQLException {
		return dao.selectMenusByUser(userOid);
	}

}
