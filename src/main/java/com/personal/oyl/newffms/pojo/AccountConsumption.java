package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class AccountConsumption extends BasePojo {
	private static final long serialVersionUID = 1L;
    private BigDecimal acntOid;
    private BigDecimal cpnOid;
    private BigDecimal amount;

    public BigDecimal getAcntOid() {
        return acntOid;
    }

    public void setAcntOid(BigDecimal acntOid) {
        this.acntOid = acntOid;
    }

    public BigDecimal getCpnOid() {
        return cpnOid;
    }

    public void setCpnOid(BigDecimal cpnOid) {
        this.cpnOid = cpnOid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
