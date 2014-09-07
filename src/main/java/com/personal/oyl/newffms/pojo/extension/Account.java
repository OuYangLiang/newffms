package com.personal.oyl.newffms.pojo.extension;

public class Account extends com.personal.oyl.newffms.pojo.Account {
    private String ownerUserName;

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getAcntTypeDesc() {
        return this.getAcntType().getDesc();
    }

    public String getAcntHumanDesc() {
        return ownerUserName + " " + this.getAcntTypeDesc() + " " + this.getAcntDesc();
    }

}
