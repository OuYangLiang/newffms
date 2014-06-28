package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class Operation extends BasePojo {
    private BigDecimal opnOid;
    private String opnDesc;
    private BigDecimal moduleOid;

    public BigDecimal getOpnOid() {
        return opnOid;
    }

    public void setOpnOid(BigDecimal opnOid) {
        this.opnOid = opnOid;
    }

    public String getOpnDesc() {
        return opnDesc;
    }

    public void setOpnDesc(String opnDesc) {
        this.opnDesc = opnDesc;
    }

    public BigDecimal getModuleOid() {
        return moduleOid;
    }

    public void setModuleOid(BigDecimal moduleOid) {
        this.moduleOid = moduleOid;
    }
}
