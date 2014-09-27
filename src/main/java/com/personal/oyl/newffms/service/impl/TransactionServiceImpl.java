package com.personal.oyl.newffms.service.impl;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.pojo.Account;
import com.personal.oyl.newffms.pojo.AccountConsumption;
import com.personal.oyl.newffms.pojo.Consumption;
import com.personal.oyl.newffms.pojo.ConsumptionForm;
import com.personal.oyl.newffms.pojo.ConsumptionItem;
import com.personal.oyl.newffms.service.AccountConsumptionService;
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

}
