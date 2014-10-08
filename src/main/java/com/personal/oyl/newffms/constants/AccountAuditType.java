package com.personal.oyl.newffms.constants;

public enum AccountAuditType {
    Add("增加"),
    Subtract("扣减");
    
    private String desc;

    private AccountAuditType(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }
}
