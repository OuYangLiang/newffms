package com.personal.oyl.newffms.pojo.key;

import java.math.BigDecimal;

public class AccountIncomingKey implements Key {
	private BigDecimal acntOid;
	private BigDecimal incomingOid;

	public AccountIncomingKey(BigDecimal acntOid, BigDecimal incomingOid) {
		super();
		this.acntOid = acntOid;
		this.incomingOid = incomingOid;
	}

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
