package com.personal.oyl.newffms.constants;

public enum AccountAuditType {
    In("AccountAuditType.In"),
    Out("AccountAuditType.Out");
    
    private String key;

    private AccountAuditType(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
}
