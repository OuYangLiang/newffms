package com.personal.oyl.newffms.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.constants.AccountAuditType;
import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.AccountAudit;
import com.personal.oyl.newffms.pojo.AccountConsumption;
import com.personal.oyl.newffms.pojo.BaseObject;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.service.AccountAuditService;
import com.personal.oyl.newffms.service.AccountConsumptionService;
import com.personal.oyl.newffms.service.AccountService;
import com.personal.oyl.newffms.service.ConsumptionItemService;
import com.personal.oyl.newffms.service.ConsumptionService;
import com.personal.oyl.newffms.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private ConsumptionService consumptionService;
    @Autowired
    private ConsumptionItemService consumptionItemService;
    @Autowired
    private AccountConsumptionService accountConsumptionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountAuditService accountAuditService;

    public void createConsumption(ConsumptionForm form) throws SQLException {
        consumptionService.insert(form.getConsumption());
        
        for (ConsumptionItem item : form.getCpnItems()) {
            item.setCpnOid(form.getConsumption().getCpnOid());
            
            consumptionItemService.insert(item);
        }
        
        for (Account acnt : form.getAccounts()) {
            AccountConsumption item = new AccountConsumption();
            item.setAcntOid(acnt.getAcntOid());
            item.setAmount(acnt.getPayment());
            item.setCpnOid(form.getConsumption().getCpnOid());
            
            accountConsumptionService.insert(item);
        }
    }

    public void updateConsumption(ConsumptionForm form) throws SQLException {
        accountConsumptionService.deleteByConsumption(form.getConsumption().getCpnOid());
        consumptionItemService.deleteByConsumption(form.getConsumption().getCpnOid());
        
        for (ConsumptionItem item : form.getCpnItems()) {
            item.setCpnOid(form.getConsumption().getCpnOid());
            
            consumptionItemService.insert(item);
        }
        
        for (Account acnt : form.getAccounts()) {
            AccountConsumption item = new AccountConsumption();
            item.setAcntOid(acnt.getAcntOid());
            item.setAmount(acnt.getPayment());
            item.setCpnOid(form.getConsumption().getCpnOid());
            
            accountConsumptionService.insert(item);
        }
        
        consumptionService.updateByPrimaryKeySelective(form.getConsumption());
    }

    public void deleteConsumption(BigDecimal cpnOid) throws SQLException {
        accountConsumptionService.deleteByConsumption(cpnOid);
        consumptionItemService.deleteByConsumption(cpnOid);
        
        consumptionService.deleteByKey(cpnOid);
    }

    public void confirmConsumption(BigDecimal cpnOid, String operator) throws SQLException {
        Date now = new Date();
        
        Consumption oldObj = consumptionService.selectByKey(cpnOid);
        
        Consumption newObj = new Consumption();
        newObj.setCpnOid(cpnOid);
        newObj.setConfirmed(true);
        newObj.setBaseObject(new BaseObject());
        newObj.getBaseObject().setUpdateTime(now);
        newObj.getBaseObject().setUpdateBy(operator);
        newObj.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        
        consumptionService.updateByPrimaryKeySelective(newObj);
        
        List<AccountConsumption> acntConsumptions = accountConsumptionService.selectByConsumption(cpnOid);
        for (AccountConsumption acntConsumption : acntConsumptions) {
            Account oldAcnt = accountService.selectByKey(acntConsumption.getAcntOid());
            oldAcnt.subtract(acntConsumption.getAmount());
            oldAcnt.getBaseObject().setUpdateTime(now);
            oldAcnt.getBaseObject().setUpdateBy(operator);
            
            oldAcnt.setAcntDesc(null);
            oldAcnt.setAcntType(null);
            oldAcnt.setQuota(null);
            oldAcnt.setOwnerOid(null);
            oldAcnt.getBaseObject().setCreateBy(null);
            oldAcnt.getBaseObject().setCreateTime(null);
            
            accountService.updateByPrimaryKeySelective(oldAcnt);
            
            AccountAudit audit = new AccountAudit();
            audit.setBaseObject(new BaseObject());
            audit.getBaseObject().setCreateBy(operator);
            audit.getBaseObject().setCreateTime(now);
            
            audit.setAdtDesc(oldObj.getCpnType().getDesc());
            audit.setAdtType(AccountAuditType.Subtract);
            audit.setAmount(acntConsumption.getAmount());
            audit.setConfirmed(true);
            audit.setAcntOid(oldAcnt.getAcntOid());
            audit.setCpnOid(oldObj.getCpnOid());
            
            accountAuditService.insert(audit);
        }
    }

    public void rollbackConsumption(BigDecimal cpnOid, String operator) throws SQLException {
        Date now = new Date();
        
        Consumption oldObj = consumptionService.selectByKey(cpnOid);
        
        Consumption newObj = new Consumption();
        newObj.setCpnOid(cpnOid);
        newObj.setConfirmed(false);
        newObj.setBaseObject(new BaseObject());
        newObj.getBaseObject().setUpdateTime(now);
        newObj.getBaseObject().setUpdateBy(operator);
        newObj.getBaseObject().setSeqNo(oldObj.getBaseObject().getSeqNo());
        
        consumptionService.updateByPrimaryKeySelective(newObj);
        accountAuditService.deleteByConsumption(cpnOid);
        
        List<AccountConsumption> acntConsumptions = accountConsumptionService.selectByConsumption(cpnOid);
        for (AccountConsumption acntConsumption : acntConsumptions) {
            Account oldAcnt = accountService.selectByKey(acntConsumption.getAcntOid());
            oldAcnt.add(acntConsumption.getAmount());
            oldAcnt.getBaseObject().setUpdateTime(now);
            oldAcnt.getBaseObject().setUpdateBy(operator);
            
            oldAcnt.setAcntDesc(null);
            oldAcnt.setAcntType(null);
            oldAcnt.setQuota(null);
            oldAcnt.setOwnerOid(null);
            oldAcnt.getBaseObject().setCreateBy(null);
            oldAcnt.getBaseObject().setCreateTime(null);
            
            accountService.updateByPrimaryKeySelective(oldAcnt);
        }
    }

}
