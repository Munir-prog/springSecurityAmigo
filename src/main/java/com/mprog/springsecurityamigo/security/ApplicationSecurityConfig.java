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

import static com.mprog.springsecurityamigo.security.ApplicationUserRole.*;

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
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        var userMunir = User.builder()
                .username("anna")
                .password(passwordEncoder.encode("anna"))
                .roles(STUDENT.name()) //ROLE_STUDENT
                .build();

        var userLinda = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("linda"))
                .roles(ADMIN.name()) //ROLE_ADMIN
                .build();

        var userTom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("tom"))
                .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .build();

        return new InMemoryUserDetailsManager(
                userMunir, userLinda, userTom
        );
    }
}
