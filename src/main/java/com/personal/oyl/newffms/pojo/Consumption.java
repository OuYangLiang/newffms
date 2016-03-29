package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.personal.oyl.newffms.constants.ConsumptionType;
import com.personal.oyl.newffms.util.DateUtil;

public class Consumption extends BasePojo {
	private static final long serialVersionUID = 1L;
    private BigDecimal cpnOid;
    private ConsumptionType cpnType;
    private BigDecimal amount;
    private Date cpnTime;
    private Boolean confirmed;
    private BaseObject baseObject;
    
    //extended field
    private String cpnTimeInput;
    private String cpnTypeDesc;
    private Date cpnTimeFrom;
    private Date cpnTimeTo;
    
    public String getCpnTimeInput() {
    	if (null != cpnTimeInput)
    		return cpnTimeInput;
    	
    	if (null != cpnTime)
    		return DateUtil.getInstance().format(cpnTime, "yyyy-MM-dd HH:mm");
    	
    	return null;
	}

	public void setCpnTimeInput(String cpnTimeInput) {
		this.cpnTimeInput = cpnTimeInput;
		
		try {
			cpnTime = DateUtil.getInstance().parse(cpnTimeInput, "yyyy-MM-dd HH:mm");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

    public Date getCpnTimeFrom() {
        return cpnTimeFrom;
    }

    public void setCpnTimeFrom(Date cpnTimeFrom) {
        this.cpnTimeFrom = cpnTimeFrom;
    }

    public Date getCpnTimeTo() {
        return cpnTimeTo;
    }

    public void setCpnTimeTo(Date cpnTimeTo) {
        this.cpnTimeTo = cpnTimeTo;
    }

    public BigDecimal getCpnOid() {
        return cpnOid;
    }

    public void setCpnOid(BigDecimal cpnOid) {
        this.cpnOid = cpnOid;
    }

    public ConsumptionType getCpnType() {
        return cpnType;
    }

    public void setCpnType(ConsumptionType cpnType) {
        this.cpnType = cpnType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCpnTime() {
		return cpnTime;
    }

    public void setCpnTime(Date cpnTime) {
        this.cpnTime = cpnTime;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
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
    
    public String getCpnTypeDesc() {
        return cpnTypeDesc;
    }

    public void setCpnTypeDesc(String cpnTypeDesc) {
        this.cpnTypeDesc = cpnTypeDesc;
    }

}
