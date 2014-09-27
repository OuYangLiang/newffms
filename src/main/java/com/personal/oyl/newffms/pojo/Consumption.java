package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.personal.oyl.newffms.constants.ConsumptionType;

public class Consumption extends BasePojo {
    private BigDecimal cpnOid;
    private ConsumptionType cpnType;
    private BigDecimal amount;
    private Date cpnTime;
    private Boolean confirmed;
    private BaseObject baseObject;
    
    //extended field
    private Integer cpnTimeSlider;
    private String cpnTypeDesc;
    
    public void calculateCpnTime() {
        int hour = cpnTimeSlider / 4;
        int minute = cpnTimeSlider % 4 * 15;
        
        Calendar c = Calendar.getInstance();
        c.setTime(cpnTime);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        this.setCpnTime(c.getTime());
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
    
    public Integer getCpnTimeSlider() {
        
        if (null == cpnTimeSlider && null != cpnTime) {
            Calendar c = Calendar.getInstance();
            c.setTime(cpnTime);
            int hour   = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            
            cpnTimeSlider = hour * 4 + (minute / 15);
        }
        
        return cpnTimeSlider;
    }

    public void setCpnTimeSlider(Integer cpnTimeSlider) {
        this.cpnTimeSlider = cpnTimeSlider;
    }

    public String getCpnTypeDesc() {
        return cpnTypeDesc;
    }

    public void setCpnTypeDesc(String cpnTypeDesc) {
        this.cpnTypeDesc = cpnTypeDesc;
    }

}
