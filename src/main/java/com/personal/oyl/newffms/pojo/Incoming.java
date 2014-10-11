package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.personal.oyl.newffms.constants.IncomingType;

public class Incoming extends BasePojo {
    private BigDecimal incomingOid;
    private String incomingDesc;
    private BigDecimal amount;
    private IncomingType incomingType;
    private Boolean confirmed;
    private BigDecimal ownerOid;
    private Date incomingDate;
    private BaseObject baseObject;
    
    //extended field
    private String owner;
    private BigDecimal acntOid;
    private String acntHumanDesc;

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

    public Date getIncomingDate() {
        return incomingDate;
    }

    public void setIncomingDate(Date incomingDate) {
        this.incomingDate = incomingDate;
    }

    public BaseObject getBaseObject() {
        return baseObject;
    }

    public void setBaseObject(BaseObject baseObject) {
        this.baseObject = baseObject;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getAcntOid() {
        return acntOid;
    }

    public void setAcntOid(BigDecimal acntOid) {
        this.acntOid = acntOid;
    }

    public String getAcntHumanDesc() {
        return acntHumanDesc;
    }

    public void setAcntHumanDesc(String acntHumanDesc) {
        this.acntHumanDesc = acntHumanDesc;
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
