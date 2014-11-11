package com.personal.oyl.newffms.pojo.key;

import java.math.BigDecimal;

public class IncomingKey implements Key {
	private BigDecimal incomingOid;

	public BigDecimal getIncomingOid() {
		return incomingOid;
	}

	public void setIncomingOid(BigDecimal incomingOid) {
		this.incomingOid = incomingOid;
	}

	public IncomingKey(BigDecimal incomingOid) {
		super();
		this.incomingOid = incomingOid;
	}
}
