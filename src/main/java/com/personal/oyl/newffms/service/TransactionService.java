package com.personal.oyl.newffms.service;

import java.sql.SQLException;

import com.personal.oyl.newffms.pojo.ConsumptionForm;

public interface TransactionService {
    public void createConsumption(ConsumptionForm form) throws SQLException;
}
