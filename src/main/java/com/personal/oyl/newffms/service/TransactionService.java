package com.personal.oyl.newffms.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.Incoming;
import com.personal.oyl.newffms.pojo.UserProfile;

public interface TransactionService {
    public void createConsumption(ConsumptionForm form) throws SQLException;
    
    public void updateConsumption(ConsumptionForm form) throws SQLException;
    
    public void deleteConsumption(BigDecimal cpnOid) throws SQLException;
    
    public void confirmConsumption(BigDecimal cpnOid, String operator) throws SQLException;
    
    public void rollbackConsumption(BigDecimal cpnOid, String operator) throws SQLException;
    
    
    public void createIncoming(Incoming form) throws SQLException;
    
    public void updateIncoming(Incoming form) throws SQLException;
    
    public void deleteIncoming(BigDecimal incomingOid) throws SQLException;
    
    public void confirmIncoming(BigDecimal incomingOid, String operator) throws SQLException;
    
    public void rollbackIncoming(BigDecimal incomingOid, String operator) throws SQLException;
    
    
    public void createAccount(Account form) throws SQLException;
    
    public void updateAccount(Account form) throws SQLException;
    
    public void deleteAccount(BigDecimal acntOid) throws SQLException;
    
    public void doAccountTransfer(BigDecimal srcAcntOid, BigDecimal targetAcntOid, BigDecimal payment, String operator) throws SQLException;
    
    
    public void updateMyProfile(UserProfile form) throws SQLException;
    
    
    public void createCategory(Category form) throws SQLException;
}
