package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class ConsumptionItem extends BasePojo {
    private BigDecimal itemOid;
    private String itemDesc;
    private BigDecimal amount;
    private BigDecimal ownerOid;
    private BigDecimal categoryOid;
    private BigDecimal cpnOid;
    
    //extended field
    private String categoryDesc;
    private String categoryFullDesc;
    private String userName;
    private Consumption consumption;

    public BigDecimal getItemOid() {
        return itemOid;
    }

    public void setItemOid(BigDecimal itemOid) {
        this.itemOid = itemOid;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOwnerOid() {
        return ownerOid;
    }

    public void setOwnerOid(BigDecimal ownerOid) {
        this.ownerOid = ownerOid;
    }

    public BigDecimal getCategoryOid() {
        return categoryOid;
    }

    public void setCategoryOid(BigDecimal categoryOid) {
        this.categoryOid = categoryOid;
    }

    public BigDecimal getCpnOid() {
        return cpnOid;
    }

    public void setCpnOid(BigDecimal cpnOid) {
        this.cpnOid = cpnOid;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategoryFullDesc() {
        return categoryFullDesc;
    }

    public void setCategoryFullDesc(String categoryFullDesc) {
        this.categoryFullDesc = categoryFullDesc;
    }

	public Consumption getConsumption() {
		return consumption;
	}

	public void setConsumption(Consumption consumption) {
		this.consumption = consumption;
	}

}
