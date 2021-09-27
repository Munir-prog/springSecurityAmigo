package com.mprog.springsecurityamigo.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mprog.springsecurityamigo.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }


    private List<ApplicationUser> getApplicationUsers(){
        return Lists.newArrayList(
                new ApplicationUser(
                        "anna",
                        passwordEncoder.encode("anna"),
                        "STUDENT"
                ),
                new ApplicationUser(
                        "linda",
                        passwordEncoder.encode("linda"),
                        "ADMIN"
                ),
                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("tom"),
                        "ADMINTRAINEE"
                )
        );
    }

}
