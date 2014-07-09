package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

import com.personal.oyl.newffms.constants.IncomingType;

public class Incoming extends BasePojo {
    private BigDecimal incomingOid;
    private String incomingDesc;
    private BigDecimal amount;
    private IncomingType incomingType;
    private Boolean confirmed;
    private BigDecimal ownerOid;
    private BaseObject baseObject;

    public BigDecimal getIncomingOid() {
        return incomingOid;
    }

    public void setIncomingOid(BigDecimal incomingOid) {
        this.incomingOid = incomingOid;
    }

    public String getIncomingDesc() {
        return incomingDesc;
    }

    public void setIncomingDesc(String incomingDesc) {
        this.incomingDesc = incomingDesc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public IncomingType getIncomingType() {
        return incomingType;
    }

    public void setIncomingType(IncomingType incomingType) {
        this.incomingType = incomingType;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public BigDecimal getOwnerOid() {
        return ownerOid;
    }

    public void setOwnerOid(BigDecimal ownerOid) {
        this.ownerOid = ownerOid;
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
