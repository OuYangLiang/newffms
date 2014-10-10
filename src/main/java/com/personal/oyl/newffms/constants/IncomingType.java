package com.personal.oyl.newffms.constants;

public enum IncomingType {
    Salary("工资"),
    Bonus("奖金"),
    Cash("礼金"),
    Other("其它");
    
    private String desc;

    private IncomingType(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }
}
