package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

import com.personal.oyl.newffms.constants.AccountType;

public class Account extends BasePojo {
    private BigDecimal acntOid;
    private String acntDesc;
    private AccountType acntType;
    private BigDecimal balance;
    private BigDecimal quota;
    private BigDecimal debt;
    private BigDecimal ownerOid;
    private BaseObject baseObject;

    public BigDecimal getAcntOid() {
        return acntOid;
    }

    public void setAcntOid(BigDecimal acntOid) {
        this.acntOid = acntOid;
    }

    public String getAcntDesc() {
        return acntDesc;
    }

    public void setAcntDesc(String acntDesc) {
        this.acntDesc = acntDesc;
    }

    public AccountType getAcntType() {
        return acntType;
    }

    public void setAcntType(AccountType acntType) {
        this.acntType = acntType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
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
