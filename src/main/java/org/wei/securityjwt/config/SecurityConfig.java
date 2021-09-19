package org.wei.securityjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.wei.securityjwt.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用 jwt 不需要 csrf
        http.csrf().disable()
                // 使用 jwt 不需要 session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 需要放行的接口
                .antMatchers("/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/user").hasRole("USER")
                // 所有请求都要认证
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }


}