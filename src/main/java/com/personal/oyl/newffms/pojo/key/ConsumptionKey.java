package com.personal.oyl.newffms.pojo.key;

import java.math.BigDecimal;

public class ConsumptionKey implements Key {
	private BigDecimal cpnOid;

	public ConsumptionKey(BigDecimal cpnOid) {
		super();
		this.cpnOid = cpnOid;
	}

	public BigDecimal getCpnOid() {
		return cpnOid;
	}

	public void setCpnOid(BigDecimal cpnOid) {
		this.cpnOid = cpnOid;
	}
}
