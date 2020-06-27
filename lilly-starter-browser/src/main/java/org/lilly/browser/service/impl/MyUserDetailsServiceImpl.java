package org.lilly.browser.service.impl;

import org.lilly.browser.service.MyUserDetailsService;
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


//        return new User("admin", new BCryptPasswordEncoder().encode("admin"),
//                true, true, true, false,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
