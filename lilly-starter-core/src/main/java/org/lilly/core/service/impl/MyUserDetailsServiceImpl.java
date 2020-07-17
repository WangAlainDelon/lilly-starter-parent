package org.lilly.core.service.impl;

import org.lilly.core.service.MyUserDetailsService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * User: Mr.Wang
 * Date: 2020/5/31
 */
@Component
public class MyUserDetailsServiceImpl implements MyUserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User("admin", new BCryptPasswordEncoder().encode("admin"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {

        return new User("admin", new BCryptPasswordEncoder().encode("admin"),
                true, true, true, false,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
