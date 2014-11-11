package com.personal.oyl.newffms.pojo.key;

import java.math.BigDecimal;

public class CategoryKey implements Key {
	private BigDecimal categoryOid;

	public CategoryKey(BigDecimal categoryOid) {
		super();
		this.categoryOid = categoryOid;
	}

	public BigDecimal getCategoryOid() {
		return categoryOid;
	}

	public void setCategoryOid(BigDecimal categoryOid) {
		this.categoryOid = categoryOid;
	}
	
	
}
