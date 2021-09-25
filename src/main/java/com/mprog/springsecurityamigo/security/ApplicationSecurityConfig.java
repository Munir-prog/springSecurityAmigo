package com.mprog.springsecurityamigo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.mprog.springsecurityamigo.security.ApplicationUserPermission.COURSE_WRITE;
import static com.mprog.springsecurityamigo.security.ApplicationUserRole.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// TODO: To learn it in detail from AMIGO
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .antMatchers(DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(POST, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(PUT, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        var userAnna = User.builder()
                .username("anna")
                .password(passwordEncoder.encode("anna"))
//                .roles(STUDENT.name()) //ROLE_STUDENT
                .authorities(STUDENT.getGranterAuthorities())
                .build();

        var userLinda = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("linda"))
//                .roles(ADMIN.name()) //ROLE_ADMIN
                .authorities(ADMIN.getGranterAuthorities())
                .build();

        var userTom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("tom"))
//                .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGranterAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                userAnna, userLinda, userTom
        );
    }
}
