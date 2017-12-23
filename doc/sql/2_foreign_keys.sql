ALTER TABLE USER_PROFILE ADD 
    FOREIGN KEY (USER_TYPE_OID)
    REFERENCES USER_TYPE(USER_TYPE_OID)
;

ALTER TABLE ROLE_PROFILE ADD 
    FOREIGN KEY (USER_TYPE_OID)
    REFERENCES USER_TYPE(USER_TYPE_OID)
;

ALTER TABLE USER_ROLE ADD 
    FOREIGN KEY (USER_OID)
    REFERENCES USER_PROFILE(USER_OID)
;

ALTER TABLE USER_ROLE ADD 
    FOREIGN KEY (ROLE_OID)
    REFERENCES ROLE_PROFILE(ROLE_OID)
;

ALTER TABLE MODULE ADD 
    FOREIGN KEY (PARENT_OID)
    REFERENCES MODULE(MODULE_OID)
;

ALTER TABLE OPERATION ADD 
    FOREIGN KEY (MODULE_OID)
    REFERENCES MODULE(MODULE_OID)
;

ALTER TABLE OPERATION_URL ADD 
    FOREIGN KEY (OPN_OID)
    REFERENCES OPERATION(OPN_OID)
;

ALTER TABLE ROLE_OPERATION ADD 
    FOREIGN KEY (ROLE_OID)
    REFERENCES ROLE_PROFILE(ROLE_OID)
;

ALTER TABLE ROLE_OPERATION ADD 
    FOREIGN KEY (OPN_OID)
    REFERENCES OPERATION(OPN_OID)
;

ALTER TABLE USER_TYPE_OPERATION ADD 
    FOREIGN KEY (USER_TYPE_OID)
    REFERENCES USER_TYPE(USER_TYPE_OID)
;

ALTER TABLE USER_TYPE_OPERATION ADD 
    FOREIGN KEY (OPN_OID)
    REFERENCES OPERATION(OPN_OID)
;

ALTER TABLE ACCOUNT ADD 
    FOREIGN KEY (OWNER_OID)
    REFERENCES USER_PROFILE(USER_OID)
;

ALTER TABLE INCOMING ADD 
    FOREIGN KEY (OWNER_OID)
    REFERENCES USER_PROFILE(USER_OID)
;

ALTER TABLE ACCOUNT_INCOMING ADD 
    FOREIGN KEY (ACNT_OID)
    REFERENCES ACCOUNT(ACNT_OID)
;

ALTER TABLE ACCOUNT_INCOMING ADD 
    FOREIGN KEY (INCOMING_OID)
    REFERENCES INCOMING(INCOMING_OID)
;

ALTER TABLE CATEGORY ADD 
    FOREIGN KEY (PARENT_OID)
    REFERENCES CATEGORY(CATEGORY_OID)
;

ALTER TABLE CONSUMPTION_ITEM ADD 
    FOREIGN KEY (OWNER_OID)
    REFERENCES USER_PROFILE(USER_OID)
;

ALTER TABLE CONSUMPTION_ITEM ADD 
    FOREIGN KEY (CPN_OID)
    REFERENCES CONSUMPTION(CPN_OID)
;

ALTER TABLE CONSUMPTION_ITEM ADD 
    FOREIGN KEY (CATEGORY_OID)
    REFERENCES CATEGORY(CATEGORY_OID)
;

ALTER TABLE ACCOUNT_CONSUMPTION ADD 
    FOREIGN KEY (CPN_OID)
    REFERENCES CONSUMPTION(CPN_OID)
;

ALTER TABLE ACCOUNT_CONSUMPTION ADD 
    FOREIGN KEY (ACNT_OID)
    REFERENCES ACCOUNT(ACNT_OID)
;

ALTER TABLE ACCOUNT_AUDIT ADD 
    FOREIGN KEY (CPN_OID)
    REFERENCES CONSUMPTION(CPN_OID)
;

ALTER TABLE ACCOUNT_AUDIT ADD 
    FOREIGN KEY (INCOMING_OID)
    REFERENCES INCOMING(INCOMING_OID)
;

ALTER TABLE ACCOUNT_AUDIT ADD 
    FOREIGN KEY (ACNT_OID)
    REFERENCES ACCOUNT(ACNT_OID)
;

ALTER TABLE ACCOUNT_AUDIT ADD 
    FOREIGN KEY (REF_ACNT_OID)
    REFERENCES ACCOUNT(ACNT_OID)
;