package com.personal.oyl.newffms.service;

import java.sql.SQLException;
import java.util.List;

import com.personal.oyl.newffms.pojo.Account;

public interface AccountService {
    public List<Account> queryAccounts() throws SQLException;
}
