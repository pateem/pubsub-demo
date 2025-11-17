package com.example.persistence;

import java.sql.Connection;

@FunctionalInterface
public interface ConnectionProvider {

  Connection getConnection();
}
