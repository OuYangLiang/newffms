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
    
    //extended field
    private String ownerUserName;
    private String acntHumanDesc;
    private BigDecimal payment;

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
    
    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getAcntTypeDesc() {
        return this.getAcntType().getDesc();
    }

    public void setAcntHumanDesc(String acntHumanDesc) {
        this.acntHumanDesc = acntHumanDesc;
    }

    public String getAcntHumanDesc() {
        if (null ==  acntHumanDesc) {
            
            if (null == ownerUserName) {
                if (null == acntType) {
                    return this.getAcntDesc();
                }
                
                return this.getAcntTypeDesc() + " " + this.getAcntDesc();
            }
            
            return ownerUserName + " " + this.getAcntTypeDesc() + " " + this.getAcntDesc();
        }
        
        return acntHumanDesc;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }
    
    public void subtract(BigDecimal value) {
        this.setBalance(balance.subtract(value));
        
        if (AccountType.Creditcard.equals(acntType)) {
            this.setDebt(debt.add(value));
        }
    }
    
    public void add(BigDecimal value) {
        this.setBalance(balance.add(value));
        
        if (AccountType.Creditcard.equals(acntType)) {
            this.setDebt(debt.subtract(value));
        }
    }

}
