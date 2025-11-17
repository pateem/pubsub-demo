package com.example.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {

  @Mock
  private Connection connection;

  @Mock
  private PreparedStatement preparedStatement;

  @Mock
  private ConnectionProvider connectionProvider;

  @InjectMocks
  private UserDaoImpl userDao;

  @Test
  void shouldDeleteAllUsers() throws Exception {
    // arrange
    var expectedSql = "DELETE FROM SUSERS";
    when(connectionProvider.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(expectedSql)).thenReturn(preparedStatement);

    // act
    userDao.deleteAll();

    // assert
    verify(preparedStatement).executeUpdate();
  }

  @Test
  void shouldSaveUser() throws Exception {
    // arrange
    User user = new User(1L, "guid-123", "John Doe");
    when(connectionProvider.getConnection()).thenReturn(connection);
    var expectedSql = "INSERT INTO SUSERS (USER_ID, USER_GUID, USER_NAME) VALUES (?, ?, ?)";
    when(connection.prepareStatement(expectedSql)).thenReturn(preparedStatement);

    // act
    userDao.save(user);

    // assert
    verify(preparedStatement).setLong(1, user.id());
    verify(preparedStatement).setString(2, user.guid());
    verify(preparedStatement).setString(3, user.name());
    verify(preparedStatement).executeUpdate();
  }

  @Test
  void shouldHandleSQLExceptionOnDeleteAll() throws Exception {
    // arrange
    when(connectionProvider.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

    // act & assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> userDao.deleteAll());
    assertEquals("Failed to delete all users", exception.getMessage());
  }

  @Test
  void shouldFindAllUsers() throws Exception {
    // arrange
    when(connectionProvider.getConnection()).thenReturn(connection);
    var expectedSql = "SELECT USER_ID, USER_GUID, USER_NAME FROM SUSERS";
    when(connection.prepareStatement(expectedSql)).thenReturn(preparedStatement);
    var resultSet = org.mockito.Mockito.mock(java.sql.ResultSet.class);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true, true, false);
    when(resultSet.getObject("USER_ID", Long.class)).thenReturn(1L, 2L);
    when(resultSet.getString("USER_GUID")).thenReturn("guid-1", "guid-2");
    when(resultSet.getString("USER_NAME")).thenReturn("User One", "User Two");

    // act
    var users = userDao.findAll();

    // assert
    assertEquals(2, users.size());
    assertEquals(1L, users.get(0).id());
    assertEquals("guid-1", users.get(0).guid());
    assertEquals("User One", users.get(0).name());
    assertEquals(2L, users.get(1).id());
    assertEquals("guid-2", users.get(1).guid());
    assertEquals("User Two", users.get(1).name());
  }

}