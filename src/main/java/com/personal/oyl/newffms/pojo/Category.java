package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Category extends BasePojo {
	private static final long serialVersionUID = 1L;
	private BigDecimal categoryOid;
    private String categoryDesc;
    private BigDecimal monthlyBudget;
    private Integer categoryLevel;
    private Boolean isLeaf;
    private BigDecimal parentOid;
    private BaseObject baseObject;
    
    //extended field
    private Category parent;
    private List<Category> subCategories;

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

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getSubCategories() {
		return subCategories;
	}
	
	public void addSubCategory(Category category) {
		if (null == subCategories) {
			subCategories = new ArrayList<Category>();
		}
		
		subCategories.add(category);
	}

	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
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

