package com.personal.oyl.newffms.pojo;

import java.util.List;

public class ConsumptionForm {
    private Consumption consumption;
    private List<ConsumptionItem> cpnItems;
    private List<Account> accounts;

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

}
