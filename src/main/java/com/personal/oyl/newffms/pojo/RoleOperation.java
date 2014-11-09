package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class RoleOperation extends BasePojo {
	private static final long serialVersionUID = 1L;
    private BigDecimal roleOid;
    private BigDecimal opnOid;

    public BigDecimal getRoleOid() {
        return roleOid;
    }

    public void setRoleOid(BigDecimal roleOid) {
        this.roleOid = roleOid;
    }

    public BigDecimal getOpnOid() {
        return opnOid;
    }

    public void setOpnOid(BigDecimal opnOid) {
        this.opnOid = opnOid;
    }
}
