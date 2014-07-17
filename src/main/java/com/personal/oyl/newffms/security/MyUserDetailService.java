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
        
        if (username.equalsIgnoreCase("oyl")) {
            AUTHORITIES.add(new SimpleGrantedAuthority("/welcome"));
            
            return new User(username, "3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2", AUTHORITIES);
        }
        
        throw new UsernameNotFoundException(username);
        
    }

}
