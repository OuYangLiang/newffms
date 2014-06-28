package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class RoleProfile extends BasePojo {
    private BigDecimal roleOid;
    private String roleDesc;
    private BigDecimal userTypeOid;

    public BigDecimal getRoleOid() {
        return roleOid;
    }

    public void setRoleOid(BigDecimal roleOid) {
        this.roleOid = roleOid;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public BigDecimal getUserTypeOid() {
        return userTypeOid;
    }

    public void setUserTypeOid(BigDecimal userTypeOid) {
        this.userTypeOid = userTypeOid;
    }
}
