package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.base.service.BaseService;
import com.personal.oyl.newffms.base.service.DbActionService;
import com.personal.oyl.newffms.base.service.PaginatingService;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.key.AccountKey;

public interface AccountService extends BaseService<Account, AccountKey>,
		DbActionService<Account, AccountKey>, PaginatingService<Account> {
	public List<Account> queryAccounts() throws SQLException;

	public List<Account> queryAccountsByConsumption(BigDecimal cpnOid)
			throws SQLException;

	public Account queryAccountsByIncoming(BigDecimal incomingOid)
			throws SQLException;

	public boolean isAccountSafeToRemove(BigDecimal acntOid)
			throws SQLException;
}
