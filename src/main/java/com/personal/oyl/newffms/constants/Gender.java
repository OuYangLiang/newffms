package com.personal.oyl.newffms.constants;

public enum Gender {
    Male("男"),
    Female("女");
    
    private String desc;

    private Gender(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }

}
