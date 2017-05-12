package ru.kpfu.itis.aekrylov.itproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 1:30 PM
 */

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                    .loginPage("/login")
                    .successForwardUrl("/")
                .and().logout()
                    .logoutUrl("/logout")
                .and().csrf()
                    .disable();
    }
}
