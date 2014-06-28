package com.personal.oyl.newffms.constants;

public enum ConsumptionType {
    Supermarket("ConsumptionType.Supermarket"),
    Online("ConsumptionType.Online"),
    Store("ConsumptionType.Store"),
    Other("ConsumptionType.Other");
    
    private String key;

    private ConsumptionType(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
}
