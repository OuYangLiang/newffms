package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class UserTypeOperation extends BasePojo {
    private BigDecimal userTypeOid;
    private BigDecimal opnOid;

    public BigDecimal getUserTypeOid() {
        return userTypeOid;
    }

    public void setUserTypeOid(BigDecimal userTypeOid) {
        this.userTypeOid = userTypeOid;
    }

    public BigDecimal getOpnOid() {
        return opnOid;
    }

    public void setOpnOid(BigDecimal opnOid) {
        this.opnOid = opnOid;
    }

}
