package com.personal.oyl.newffms.pojo.key;

import java.math.BigDecimal;

public class AccountAuditKey implements Key{
	private BigDecimal adtOid;

	public AccountAuditKey(BigDecimal adtOid) {
		super();
		this.adtOid = adtOid;
	}

	public BigDecimal getAdtOid() {
		return adtOid;
	}

	public void setAdtOid(BigDecimal adtOid) {
		this.adtOid = adtOid;
	}
}
