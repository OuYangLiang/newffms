package com.personal.oyl.newffms.constants;

public enum AccountType {
    Cash("AccountType.Cash"),
    Bankcard("AccountType.Bankcard"),
    Creditcard("AccountType.Creditcard"),
    Alipay("AccountType.Alipay"),
    Epp("AccountType.Epp"),
    MedicalInsurance("AccountType.MedicalInsurance"),
    Accumulation("AccountType.Accumulation"),
    Other("AccountType.Other");
    
    private String key;

    private AccountType(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
}
