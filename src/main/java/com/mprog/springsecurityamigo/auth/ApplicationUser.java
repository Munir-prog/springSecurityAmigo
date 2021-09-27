package com.mprog.springsecurityamigo.auth;

import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Value
@Builder
public class ApplicationUser {

    private final String username;
    private final String password;
    private final String role;
}
