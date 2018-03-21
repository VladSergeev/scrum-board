package com.startup.scrumboard.service.security;

import com.startup.scrumboard.model.entity.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {

            UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

            String userName = String.valueOf(auth.getPrincipal());
            String password = String.valueOf(auth.getCredentials());

            User user = new User();

            //TODO:Дабы было Денису проще работать!
            user.setLogin("root");
            user.setPassword("123456");
            user.setFirstName("Имя");
            user.setMiddleName("Отчество");
            user.setLastName("Фамилия");

            List<GrantedAuthority> grantedAuthorityList = new ArrayList();
            if (user.getLogin().equals(userName) && user.getPassword().equals(password)) {
                grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
            } else {
                throw new BadCredentialsException("Неверное имя пользователя или пароль");
            }

            user.setPassword(null);
            return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorityList);

        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
