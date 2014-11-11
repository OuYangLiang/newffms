package com.personal.oyl.newffms.pojo.key;

import java.math.BigDecimal;

public class UserProfileKey implements Key {
	private BigDecimal userOid;

	public UserProfileKey(BigDecimal userOid) {
		super();
		this.userOid = userOid;
	}

	public BigDecimal getUserOid() {
		return userOid;
	}

	public void setUserOid(BigDecimal userOid) {
		this.userOid = userOid;
	}
}
