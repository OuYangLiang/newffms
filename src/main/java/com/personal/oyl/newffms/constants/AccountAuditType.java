package com.personal.oyl.newffms.constants;

public enum AccountAuditType {
    Add("AccountAuditType.Add"),
    Subtract("AccountAuditType.Subtract");
    
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
