package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.personal.oyl.newffms.constants.AccountAuditType;

public class AccountAudit extends BasePojo {
	private static final long serialVersionUID = 1L;
    private BigDecimal adtOid;
    private String adtDesc;
    private AccountAuditType adtType;
    private Date adtTime;
    private BigDecimal amount;
    private Boolean confirmed;
    private BigDecimal acntOid;
    private BigDecimal refAcntOid;
    private BigDecimal incomingOid;
    private BigDecimal cpnOid;
    private BaseObject baseObject;
    
    //extended field
    private Date createTimeFrom;
    private Date createTimeTo;
    
    public BigDecimal getAdtOid() {
        return adtOid;
    }

    public void setAdtOid(BigDecimal adtOid) {
        this.adtOid = adtOid;
    }

    public String getAdtDesc() {
        return adtDesc;
    }

    public void setAdtDesc(String adtDesc) {
        this.adtDesc = adtDesc;
    }

    public AccountAuditType getAdtType() {
        return adtType;
    }

    public void setAdtType(AccountAuditType adtType) {
        this.adtType = adtType;
    }

    public Date getAdtTime() {
		return adtTime;
	}

	public void setAdtTime(Date adtTime) {
		this.adtTime = adtTime;
	}

	public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public BigDecimal getAcntOid() {
        return acntOid;
    }

    public void setAcntOid(BigDecimal acntOid) {
        this.acntOid = acntOid;
    }

    public BigDecimal getRefAcntOid() {
        return refAcntOid;
    }

    public void setRefAcntOid(BigDecimal refAcntOid) {
        this.refAcntOid = refAcntOid;
    }

    public BigDecimal getIncomingOid() {
        return incomingOid;
    }

    public void setIncomingOid(BigDecimal incomingOid) {
        this.incomingOid = incomingOid;
    }

    public BigDecimal getCpnOid() {
        return cpnOid;
    }

    public void setCpnOid(BigDecimal cpnOid) {
        this.cpnOid = cpnOid;
    }

    public BaseObject getBaseObject() {
        return baseObject;
    }

    public void setBaseObject(BaseObject baseObject) {
        this.baseObject = baseObject;
    }
    
    public Date getCreateTimeFrom() {
        return createTimeFrom;
    }

    public void setCreateTimeFrom(Date createTimeFrom) {
        this.createTimeFrom = createTimeFrom;
    }

    public Date getCreateTimeTo() {
        return createTimeTo;
    }

    public void setCreateTimeTo(Date createTimeTo) {
        this.createTimeTo = createTimeTo;
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
