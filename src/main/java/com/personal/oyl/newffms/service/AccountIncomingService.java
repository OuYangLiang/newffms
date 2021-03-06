package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.pojo.AccountIncoming;
import com.personal.oyl.newffms.pojo.key.AccountIncomingKey;

public interface AccountIncomingService extends
		BaseService<AccountIncoming, AccountIncomingKey>,
		DbActionService<AccountIncoming, AccountIncomingKey> {

	public AccountIncoming selectByIncoming(BigDecimal incomingOid)
			throws SQLException;

	public void deleteByIncoming(BigDecimal incomingOid) throws SQLException;
}
