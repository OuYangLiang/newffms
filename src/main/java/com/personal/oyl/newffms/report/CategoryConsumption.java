package com.personal.oyl.newffms.report;

import java.math.BigDecimal;

import com.personal.oyl.newffms.pojo.Category;
import com.personal.oyl.newffms.pojo.UserProfile;

public class CategoryConsumption {
    private BigDecimal userOid;
    private String userName;
    private BigDecimal categoryOid;
    private String categoryDesc;
    private Boolean isLeaf;
    private Integer categoryLevel;
    private BigDecimal parentOid;
    private BigDecimal total;
    
    public static CategoryConsumption init(Category category) {
        CategoryConsumption rlt = new CategoryConsumption();
        rlt.setUserOid(BigDecimal.valueOf(-1));
        rlt.setCategoryOid(category.getCategoryOid());
        rlt.setCategoryDesc(category.getCategoryDesc());
        rlt.setIsLeaf(category.getIsLeaf());
        rlt.setCategoryLevel(category.getCategoryLevel());
        rlt.setParentOid(category.getParentOid());
        rlt.setTotal(BigDecimal.ZERO);
        
        return rlt;
    }
    
    public static CategoryConsumption init(Category category, UserProfile user) {
        CategoryConsumption rlt = new CategoryConsumption();
        rlt.setUserOid(user.getUserOid());
        rlt.setUserName(user.getUserName());
        rlt.setCategoryOid(category.getCategoryOid());
        rlt.setCategoryDesc(category.getCategoryDesc());
        rlt.setIsLeaf(category.getIsLeaf());
        rlt.setCategoryLevel(category.getCategoryLevel());
        rlt.setParentOid(category.getParentOid());
        rlt.setTotal(BigDecimal.ZERO);
        
        return rlt;
    }

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

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public BigDecimal getParentOid() {
        return parentOid;
    }

    public void setParentOid(BigDecimal parentOid) {
        this.parentOid = parentOid;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
