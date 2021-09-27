package com.mprog.springsecurityamigo.auth;

import com.google.common.collect.Lists;
import com.mprog.springsecurityamigo.db.ConnectionManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import static com.mprog.springsecurityamigo.security.ApplicationUserRole.*;

@Repository("good")
@RequiredArgsConstructor
public class GoodApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;
    private final ConnectionManager connectionManager;
    private static final String FIND_BY_USERNAME = """
            SELECT * FROM users WHERE username = ?
            """;

    @Override
    @SneakyThrows
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        ApplicationUser user = null;
        try (var connection = connectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_USERNAME)) {
            preparedStatement.setObject(1, username);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);
        }
    }

    @SneakyThrows
    private ApplicationUser buildUser(ResultSet resultSet) {
        return ApplicationUser.builder()
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .role(resultSet.getString("role"))
                .build();
    }
}
