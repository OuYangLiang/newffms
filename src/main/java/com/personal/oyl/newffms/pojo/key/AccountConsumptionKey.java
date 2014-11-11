package com.personal.oyl.newffms.pojo.key;

import java.math.BigDecimal;

public class AccountConsumptionKey implements Key {
	private BigDecimal acntOid;
	private BigDecimal cpnOid;

	public AccountConsumptionKey(BigDecimal acntOid, BigDecimal cpnOid) {
		super();
		this.acntOid = acntOid;
		this.cpnOid = cpnOid;
	}

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

}
