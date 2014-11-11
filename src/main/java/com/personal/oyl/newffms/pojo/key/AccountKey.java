package com.personal.oyl.newffms.pojo.key;

import java.math.BigDecimal;

public class AccountKey implements Key {
	private BigDecimal acntOid;

	public BigDecimal getAcntOid() {
		return acntOid;
	}

	public void setAcntOid(BigDecimal acntOid) {
		this.acntOid = acntOid;
	}

	public AccountKey(BigDecimal acntOid) {
		super();
		this.acntOid = acntOid;
	}
}
