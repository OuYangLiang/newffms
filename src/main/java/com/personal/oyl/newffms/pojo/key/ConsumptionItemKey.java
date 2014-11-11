package com.personal.oyl.newffms.pojo.key;

import java.math.BigDecimal;

public class ConsumptionItemKey implements Key {
	private BigDecimal itemOid;

	public BigDecimal getItemOid() {
		return itemOid;
	}

	public void setItemOid(BigDecimal itemOid) {
		this.itemOid = itemOid;
	}

	public ConsumptionItemKey(BigDecimal itemOid) {
		super();
		this.itemOid = itemOid;
	}
}
