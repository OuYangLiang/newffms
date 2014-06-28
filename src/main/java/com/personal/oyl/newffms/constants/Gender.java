package com.personal.oyl.newffms.constants;

public enum Gender {
    Male("Gender.Male"),
    Female("Gender.Female");
    
    private String key;

    private Gender(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }

}
