package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class UserType extends BasePojo {
    private BigDecimal userTypeOid;
    private String userTypeDesc;

    public BigDecimal getUserTypeOid() {
        return userTypeOid;
    }

    public void setUserTypeOid(BigDecimal userTypeOid) {
        this.userTypeOid = userTypeOid;
    }

    public String getUserTypeDesc() {
        return userTypeDesc;
    }

    public void setUserTypeDesc(String userTypeDesc) {
        this.userTypeDesc = userTypeDesc;
    }
}
