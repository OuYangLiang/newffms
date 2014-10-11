package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class AccountIncoming extends BasePojo {
    private BigDecimal acntOid;
    private BigDecimal incomingOid;

    public BigDecimal getAcntOid() {
        return acntOid;
    }

    public void setAcntOid(BigDecimal acntOid) {
        this.acntOid = acntOid;
    }

    public BigDecimal getIncomingOid() {
        return incomingOid;
    }

    public void setIncomingOid(BigDecimal incomingOid) {
        this.incomingOid = incomingOid;
    }

}
