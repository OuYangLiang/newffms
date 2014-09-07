package com.personal.oyl.newffms.service;

import java.sql.SQLException;
import java.util.List;

public interface AccountService {
    public List<com.personal.oyl.newffms.pojo.extension.Account> queryAccounts() throws SQLException;
}
