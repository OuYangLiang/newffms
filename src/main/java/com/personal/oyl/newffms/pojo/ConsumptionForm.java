package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConsumptionForm {
    private Consumption consumption;
    private List<ConsumptionItem> cpnItems;
    private List<Account> accounts;
    
    public ConsumptionForm() {
        setConsumption(new Consumption());
        getConsumption().setCpnTimeSlider(1);
        setCpnItems(new ArrayList<ConsumptionItem>());
        setAccounts(new ArrayList<Account>());
        getCpnItems().add(new ConsumptionItem());
        getAccounts().add(new Account());
    }

    public Consumption getConsumption() {
        return consumption;
    }

    public void setConsumption(Consumption consumption) {
        this.consumption = consumption;
    }

    public List<ConsumptionItem> getCpnItems() {
        return cpnItems;
    }

    public void setCpnItems(List<ConsumptionItem> cpnItems) {
        this.cpnItems = cpnItems;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    public BigDecimal getTotalItemAmount() {
        if (null == cpnItems) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal rlt = BigDecimal.ZERO;
        for (ConsumptionItem item : this.cpnItems) {
            rlt = rlt.add(null == item.getAmount() ? BigDecimal.ZERO : item.getAmount());
        }
        
        return rlt;
    }
    
    public BigDecimal getTotalPayment() {
        if (null == accounts) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal rlt = BigDecimal.ZERO;
        for (Account item : this.accounts) {
            rlt = rlt.add(item.getPayment());
        }
        
        return rlt;
    }

}
