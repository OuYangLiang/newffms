package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

public class Category extends BasePojo {
    private BigDecimal categoryOid;
    private String categoryDesc;
    private BigDecimal monthlyBudget;
    private Integer categoryLevel;
    private Boolean isLeaf;
    private BigDecimal parentOid;
    private BaseObject baseObject;
    
    //extended field
    private String parentCategoryDesc;
    private String parentCategoryFullDesc;

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

    public BigDecimal getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(BigDecimal monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public BigDecimal getParentOid() {
        return parentOid;
    }

    public void setParentOid(BigDecimal parentOid) {
        this.parentOid = parentOid;
    }

    public BaseObject getBaseObject() {
        return baseObject;
    }

    public void setBaseObject(BaseObject baseObject) {
        this.baseObject = baseObject;
    }

    public String getParentCategoryDesc() {
		return parentCategoryDesc;
	}

	public void setParentCategoryDesc(String parentCategoryDesc) {
		this.parentCategoryDesc = parentCategoryDesc;
	}

	public String getParentCategoryFullDesc() {
		return parentCategoryFullDesc;
	}

	public void setParentCategoryFullDesc(String parentCategoryFullDesc) {
		this.parentCategoryFullDesc = parentCategoryFullDesc;
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
    
    public String getCategoryOidDesc() {
    	if (null == categoryOid)
    		return null;
    	
        return this.categoryOid.toString();
    }
    
    public String getParentOidDesc() {
        if (null == parentOid)
            return null;
        
        return this.parentOid.toString();
    }

}

