package com.personal.oyl.newffms.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
        
        if (username.equals("oyl")) {
            AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
            
            return new User(username, "password123", AUTHORITIES);
        }
        
        throw new UsernameNotFoundException(username);
        
    }

}
