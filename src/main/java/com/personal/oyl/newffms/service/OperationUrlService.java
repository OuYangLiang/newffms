package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OperationUrlService {
	List<String> selectUrlsByUser(BigDecimal userOid) throws SQLException;
}
