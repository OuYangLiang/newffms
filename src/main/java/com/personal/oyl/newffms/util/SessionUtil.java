package com.personal.oyl.newffms.util;

import javax.servlet.http.HttpSession;

import com.personal.oyl.newffms.constants.Constants;
import com.personal.oyl.newffms.pojo.UserProfile;

public class SessionUtil {
    public UserProfile getLoginUser(HttpSession session) {
        return (UserProfile) session.getAttribute(Constants.SESSION_USER_KEY);
    }
    
    private static SessionUtil instance;
    private SessionUtil() {
        
    }
    
    public static SessionUtil getInstance() {
        if (null == instance) {
            synchronized(SessionUtil.class) {
                if (null == instance) {
                    instance = new SessionUtil();
                }
            }
        }
        
        return instance;
    }
}
