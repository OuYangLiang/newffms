package com.personal.oyl.newffms.report;

import java.math.BigDecimal;

public class PersonalConsumption {
    private BigDecimal userOid;
    private String userName;
    private BigDecimal categoryOid;
    private BigDecimal total;

    public BigDecimal getUserOid() {
        return userOid;
    }

    public void setUserOid(BigDecimal userOid) {
        this.userOid = userOid;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getCategoryOid() {
        return categoryOid;
    }

    public void setCategoryOid(BigDecimal categoryOid) {
        this.categoryOid = categoryOid;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
