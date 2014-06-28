package com.personal.oyl.newffms.constants;

public enum IncomingType {
    Salary("IncomingType.Salary"),
    Bonus("IncomingType.Bonus"),
    Cash("IncomingType.Cash"),
    Other("IncomingType.Other");
    
    private String key;

    private IncomingType(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
}
