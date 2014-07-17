package com.personal.oyl.newffms.security;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

public class CustomAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {
    private static final Logger log = LoggerFactory.getLogger(CustomAccessDecisionVoter.class);
    
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        String url = object.getRequestUrl().toString();  
          
        log.debug(url);  
          
        Iterator<? extends GrantedAuthority> it = authentication.getAuthorities().iterator();  
          
        while (it.hasNext())  
        {  
            GrantedAuthority authority = it.next();  
              
            if (url.contains(authority.getAuthority()))  
                return ACCESS_GRANTED;  
        }  
          
        return ACCESS_DENIED;  
    }

}
