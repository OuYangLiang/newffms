package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class OperationUrl extends BasePojo {
	private static final long serialVersionUID = 1L;
    private BigDecimal opnOid;
    private String opnUrl;

    public BigDecimal getOpnOid() {
        return opnOid;
    }

    public void setOpnOid(BigDecimal opnOid) {
        this.opnOid = opnOid;
    }

    public String getOpnUrl() {
        return opnUrl;
    }

    public void setOpnUrl(String opnUrl) {
        this.opnUrl = opnUrl;
    }
}
