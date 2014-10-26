package com.personal.oyl.newffms.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OperationUrlDao {
	List<String> selectUrlsByUser(BigDecimal userOid) throws SQLException;
}
