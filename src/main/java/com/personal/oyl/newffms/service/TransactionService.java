package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.pojo.ConsumptionForm;

public interface TransactionService {
    public void createConsumption(ConsumptionForm form) throws SQLException;
    
    public void updateConsumption(ConsumptionForm form) throws SQLException;
    
    public void deleteConsumption(BigDecimal cpnOid) throws SQLException;
    
    public void confirmConsumption(BigDecimal cpnOid) throws SQLException;
    
    public void rollbackConsumption(BigDecimal cpnOid) throws SQLException;
}
