package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeRequests()

                        .antMatchers("/web/**").permitAll()
                        .antMatchers("/api/**").hasAuthority("ADMIN")
                        .antMatchers("/web/accounts/**").hasAuthority("ADMIN")
                        .antMatchers("/h2-console").hasAuthority("ADMIN")
                        .antMatchers("/web/account/**").hasAuthority("CLIENT");

                /*.antMatchers("/web/index.html","/web/css/**","/web/img/**","/web/js/**","/api/login").permitAll()
                .antMatchers("/web/accounts").hasAuthority("CLIENT")
                .antMatchers("/web/account").hasAuthority("CLIENT")
                .antMatchers("/web/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console").hasAuthority("ADMIN");*/

        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");


        // turn off checking for CSRF tokens

        http.csrf().disable();


        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());


        http.logout().logoutUrl("api/logout");

        return http.build();
    }




    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}
