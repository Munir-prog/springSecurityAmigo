package com.mprog.springsecurityamigo.db;


import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "db")
public class ConnectionManager {

    private String url;

    private String username;

    private String password;

    static {
        loadDriver();
    }

    @SneakyThrows
    private static void loadDriver() {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    @SneakyThrows
    public Connection get() {
        return DriverManager
                .getConnection(url, username, password);
    }
}
