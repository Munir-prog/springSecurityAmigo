package com.mprog.springsecurityamigo.auth;

import com.mprog.springsecurityamigo.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    @Autowired
    public ApplicationUserService(@Qualifier("good") ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var applicationUser = applicationUserDao.selectApplicationUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        return buildApplicationUserDto(applicationUser);
    }

    private ApplicationUserDto buildApplicationUserDto(ApplicationUser applicationUser) {
        var role = applicationUser.getRole();
        var applicationUserRole = ApplicationUserRole.valueOf(role);

        return new ApplicationUserDto(
                applicationUser.getUsername(),
                applicationUser.getPassword(),
                applicationUserRole.getGranterAuthorities(),
                true,
                true,
                true,
                true
        );
    }
}
