package org.lilly.core.authentication.mobile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lilly.core.service.MyUserDetailsService;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * 短信登录的校验类 Provider
 * 模仿的是表单登录的Provider: DaoAuthenticationProvider
 */

public class SmsAuthenticationProvider implements AuthenticationProvider {

    protected final Log logger = LogFactory.getLog(getClass());
    // ~ Instance fields
    // ================================================================================================
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private MyUserDetailsService myUserDetailsService;


    // ~ Methods
    // ========================================================================================================

    /**
     * Performs authentication with the same contract as
     * {@link org.springframework.security.authentication.AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        //SmsAuthenticationProvider只支持SmsAuthenticationToken类型的Token
        Assert.isInstanceOf(SmsAuthenticationToken.class, authentication,
                messages.getMessage(
                        "SmsAuthenticationProvider.onlySupports",
                        "Only SmsAuthenticationToken is supported"));

        String mobile = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
                : authentication.getName();

        //根据mobile查询用户的信息。
        UserDetails user = null;
        try {
            user = retrieveUser(mobile,
                    (SmsAuthenticationToken) authentication);
        } catch (UsernameNotFoundException notFound) {
            logger.debug("User '" + mobile + "' not found");
        }

        Assert.notNull(user,
                "retrieveUser returned null - a violation of the interface contract");
        //封装认证后的token
        SmsAuthenticationToken result = new SmsAuthenticationToken(
                mobile, authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        return result;
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    public boolean supports(Class<?> authentication) {
        //isAssignableFrom  SmsAuthenticationToken 对象所表示的类或接口与指定的authentication
        // 参数所表示的类或接口是否相同，或是否是其超类或超接口
        return (SmsAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    protected final UserDetails retrieveUser(String mobile,
                                             SmsAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;

        try {
            loadedUser = this.getMyUserDetailsService().loadUserByMobile(mobile);
        } catch (AuthenticationException notFound) {
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }


    public GrantedAuthoritiesMapper getAuthoritiesMapper() {
        return authoritiesMapper;
    }

    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
        this.authoritiesMapper = authoritiesMapper;
    }

    public MyUserDetailsService getMyUserDetailsService() {
        return myUserDetailsService;
    }

    public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }
}