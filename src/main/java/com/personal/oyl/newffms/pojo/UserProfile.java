package com.personal.oyl.newffms.pojo;

import java.math.BigDecimal;

import com.personal.oyl.newffms.constants.Gender;

public class UserProfile extends BasePojo {
	private static final long serialVersionUID = 1L;
    private BigDecimal userOid;
    private String userName;
    private String userAlias;
    private Gender gender;
    private String phone;
    private String email;
    private String loginId;
    private String loginPwd;
    private BigDecimal userTypeOid;
    private BaseObject baseObject;
    
    //extended field
    private Boolean changePwd;
    private String loginPwdOrigin;
    private String loginPwdNew;
    private String loginPwdConfirm;

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

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public BigDecimal getUserTypeOid() {
        return userTypeOid;
    }

    public void setUserTypeOid(BigDecimal userTypeOid) {
        this.userTypeOid = userTypeOid;
    }

    public BaseObject getBaseObject() {
        return baseObject;
    }

    public void setBaseObject(BaseObject baseObject) {
        this.baseObject = baseObject;
    }

    public Boolean getChangePwd() {
		return changePwd;
	}

	public void setChangePwd(Boolean changePwd) {
		this.changePwd = changePwd;
	}

	public String getLoginPwdOrigin() {
		return loginPwdOrigin;
	}

	public void setLoginPwdOrigin(String loginPwdOrigin) {
		this.loginPwdOrigin = loginPwdOrigin;
	}

	public String getLoginPwdNew() {
		return loginPwdNew;
	}

	public void setLoginPwdNew(String loginPwdNew) {
		this.loginPwdNew = loginPwdNew;
	}

	public String getLoginPwdConfirm() {
		return loginPwdConfirm;
	}

	public void setLoginPwdConfirm(String loginPwdConfirm) {
		this.loginPwdConfirm = loginPwdConfirm;
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
