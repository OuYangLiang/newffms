package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class Module extends BasePojo {
	private static final long serialVersionUID = 1L;
    private BigDecimal moduleOid;
    private String moduleDesc;
    private Integer moduleLevel;
    private Integer showOrder;
    private String moduleLink;
    private BigDecimal parentOid;

    public BigDecimal getModuleOid() {
        return moduleOid;
    }

    public void setModuleOid(BigDecimal moduleOid) {
        this.moduleOid = moduleOid;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public Integer getModuleLevel() {
        return moduleLevel;
    }

    public void setModuleLevel(Integer moduleLevel) {
        this.moduleLevel = moduleLevel;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getModuleLink() {
        return moduleLink;
    }

    public void setModuleLink(String moduleLink) {
        this.moduleLink = moduleLink;
    }

    public BigDecimal getParentOid() {
        return parentOid;
    }

    public void setParentOid(BigDecimal parentOid) {
        this.parentOid = parentOid;
    }
    
}
