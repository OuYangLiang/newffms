package com.personal.oyl.newffms.constants;

public enum AccountType {
    Cash("现金"),
    Bankcard("银行卡"),
    Creditcard("信用卡"),
    Alipay("支付宝"),
    Epp("易付宝"),
    MedicalInsurance("医保"),
    Accumulation("公积金"),
    Other("其它");
    
    private String desc;

    private AccountType(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }
}
