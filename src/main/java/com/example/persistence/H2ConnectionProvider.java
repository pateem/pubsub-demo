package com.example.persistence;

import java.sql.Connection;

public class H2ConnectionProvider implements ConnectionProvider {
    @Override
    public Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            return java.sql.DriverManager.getConnection("jdbc:h2:mem:testdb;" +
                            "DB_CLOSE_DELAY=-1;" +
                            "INIT=RUNSCRIPT FROM 'classpath:/sql/init_users.sql'",
                    "sa",
                    "");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get H2 connection", e);
        }
    }
}
