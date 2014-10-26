package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.dao.OperationUrlDao;
import com.personal.oyl.newffms.service.OperationUrlService;

public class OperationUrlServiceImpl implements OperationUrlService {
	
	@Autowired
	private OperationUrlDao dao;

	public List<String> selectUrlsByUser(BigDecimal userOid)
			throws SQLException {
		return dao.selectUrlsByUser(userOid);
	}

}
