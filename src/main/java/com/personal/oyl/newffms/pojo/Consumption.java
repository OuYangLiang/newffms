package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.personal.oyl.newffms.constants.ConsumptionType;

public class Consumption extends BasePojo {
    private BigDecimal cpnOid;
    private ConsumptionType cpnType;
    private BigDecimal amount;
    private Date cpnTime;
    private Boolean confirmed;
    private BaseObject baseObject;

    public BigDecimal getCpnOid() {
        return cpnOid;
    }

    public void setCpnOid(BigDecimal cpnOid) {
        this.cpnOid = cpnOid;
    }

    public ConsumptionType getCpnType() {
        return cpnType;
    }

    public void setCpnType(ConsumptionType cpnType) {
        this.cpnType = cpnType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCpnTime() {
        return cpnTime;
    }

    public void setCpnTime(Date cpnTime) {
        this.cpnTime = cpnTime;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public BaseObject getBaseObject() {
        return baseObject;
    }

    public void setBaseObject(BaseObject baseObject) {
        this.baseObject = baseObject;
    }
    
    @Override
    public void setAllEmptyStringToNull() throws Exception {
        if (null != baseObject) {
            baseObject.setAllEmptyStringToNull();
        }
        
        super.setAllEmptyStringToNull();
    }

    @Override
    public void trimAllString() throws Exception {
        if (null != baseObject) {
            baseObject.trimAllString();
        }
        super.trimAllString();
    }
}
