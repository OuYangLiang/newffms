package com.personal.oyl.newffms.security;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.personal.oyl.newffms.constants.Constants;
import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.service.UserProfileService;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);
    
    @Autowired
    private UserProfileService userProfileService;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        try {
            UserProfile user = userProfileService.selectByLoginId(authentication.getName());
            
            request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
            
            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, "/welcome");
            
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        
    }

}
