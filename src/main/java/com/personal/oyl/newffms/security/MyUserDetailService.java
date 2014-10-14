package com.personal.oyl.newffms.security;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.personal.oyl.newffms.pojo.UserProfile;
import com.personal.oyl.newffms.service.UserProfileService;

public class MyUserDetailService implements UserDetailsService {
    
    private static final Logger log = LoggerFactory.getLogger(MyUserDetailService.class);
    
    @Autowired
    private UserProfileService userProfileService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile user = null;
        
        try {
            user = userProfileService.selectByLoginId(username.trim());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            
            throw new UsernameNotFoundException(username, e);
        }
        
        if (null == user) {
            throw new UsernameNotFoundException(username);
        }
        
        List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
        AUTHORITIES.add(new SimpleGrantedAuthority("/welcome"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/accessDenied"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/test/visit"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/test/viewUser"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/user/add"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/user/saveAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/summary"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/search"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/listOfSummary"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/listOfItemSummary"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/initAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/confirmAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/saveAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/view"));
        
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/delete"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/confirm"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/rollback"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/initEdit"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/confirmEdit"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/consumption/saveEdit"));
        
        
        AUTHORITIES.add(new SimpleGrantedAuthority("/report/consumption"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/report/incoming"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/report/consumptionDataSource"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/report/incomingDataSource"));
        
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/ajaxGetAllAccounts"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/category/ajaxGetAllCategories"));
        
        
        
        
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/summary"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/search"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/listOfSummary"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/initAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/confirmAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/saveAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/view"));
        
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/delete"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/confirm"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/rollback"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/initEdit"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/confirmEdit"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/incoming/saveEdit"));
        
        
        
        
        
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/summary"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/search"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/listOfSummary"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/initAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/confirmAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/saveAdd"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/view"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/viewDetails"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/delete"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/initEdit"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/confirmEdit"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/saveEdit"));
        
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/initTransfer"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/confirmTransfer"));
        AUTHORITIES.add(new SimpleGrantedAuthority("/account/saveTransfer"));
        
        return new User(username, user.getLoginPwd(), AUTHORITIES);
    }

}
