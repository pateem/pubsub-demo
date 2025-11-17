package com.example.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

  private static final String USERS_TABLE_NAME = "SUSERS";
  private static final String ID_COLUMN = "USER_ID";
  private static final String GUID_COLUMN = "USER_GUID";
  private static final String NAME_COLUMN = "USER_NAME";

  /* SQL templates */
  private static final String SELECT_TEMPLATE = "SELECT %s, %s, %s FROM %s";
  private static final String INSERT_TEMPLATE = "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)";
  private static final String DELETE_TEMPLATE = "DELETE FROM %s";

  private final ConnectionProvider connectionProvider;

  public UserDaoImpl(ConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  @Override
  public List<User> findAll() {
    String sql = String.format(SELECT_TEMPLATE, ID_COLUMN, GUID_COLUMN, NAME_COLUMN,
        USERS_TABLE_NAME);
    try (Connection connection = connectionProvider.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {

      List<User> users = new java.util.ArrayList<>();
      while (resultSet.next()) {
        users.add(mapUserRow(resultSet));
      }
      return users;
    } catch (Exception e) {
      throw new RuntimeException("Failed to retrieve users", e);
    }
  }

  private User mapUserRow(ResultSet rs) throws SQLException {
    Long id = rs.getObject(ID_COLUMN, Long.class);
    String guid = rs.getString(GUID_COLUMN);
    String name = rs.getString(NAME_COLUMN);
    return new User(id, guid, name);
  }

  @Override
  public void save(User user) {
    String sql = String.format(INSERT_TEMPLATE, USERS_TABLE_NAME, ID_COLUMN, GUID_COLUMN,
        NAME_COLUMN);
    try (Connection connection = connectionProvider.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setLong(1, user.id());
      statement.setString(2, user.guid());
      statement.setString(3, user.name());
      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Failed to save user", e);
    }
  }

  @Override
  public void deleteAll() {
    String sql = String.format(DELETE_TEMPLATE, USERS_TABLE_NAME);
    try (Connection connection = connectionProvider.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException("Failed to delete all users", e);
    }
  }
}