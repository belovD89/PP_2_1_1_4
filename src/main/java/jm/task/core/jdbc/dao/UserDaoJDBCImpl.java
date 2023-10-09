//package jm.task.core.jdbc.dao;
//
//import jm.task.core.jdbc.model.User;
//import jm.task.core.jdbc.util.Util;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import java.sql.SQLException;
//
//import static jm.task.core.jdbc.util.Util.getConnection;
//
//public class UserDaoJDBCImpl implements UserDao {
//    //вызов статического метода getConnection() у класса Util(соединение с БД)
//    private final Connection connection = Util.getConnection();  // статик финал поле для подключения к БД
//
//    public UserDaoJDBCImpl() {
//
//    }
//
//    // создание таблицы, если не создана
//    public void createUsersTable() throws SQLException {
//        try {
//            // Начать транзакцию
//            connection.setAutoCommit(false);
//
//            String createTable = "CREATE TABLE usersTable (" +
//                    "  id INTEGER NOT NULL AUTO_INCREMENT ," +
//                    "  name VARCHAR(45) NOT NULL," +
//                    "  last_name VARCHAR(45) NOT NULL," +
//                    "  age INT NOT NULL," +
//                    "  PRIMARY KEY  (id)," +
//                    "  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);";
//
//            try (Statement statement = connection.createStatement()) {
//                statement.executeUpdate(createTable);
//                System.out.println("Таблица создана");
//
//                // Фиксация транзакции, если операция прошла успешно
//                connection.commit();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.err.println("При создании таблицы произошла ошибка");
//
//                // Откат транзакции в случае ошибки
//                connection.rollback();
//            } finally {
//                // Включение автоматической фиксации и возврат соединения в исходное состояние
//                connection.setAutoCommit(true);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Произошла ошибка при настройке транзакции для создания таблицы");
//        }
//    }
//
//    // удаление таблицы, если она уже существует
//    public void dropUsersTable() throws SQLException {
//        try {
//            // Начать транзакцию
//            connection.setAutoCommit(false);
//
//            try (Statement statement = connection.createStatement()) {
//                statement.executeUpdate("DROP TABLE IF EXISTS usersTable");
//                System.out.println("Удалена старая таблица");
//
//                // Фиксация транзакции, если операция прошла успешно
//                connection.commit();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.err.println("Ошибка при удалении таблицы");
//
//                // Откат транзакции в случае ошибки
//                connection.rollback();
//            } finally {
//                // Включение автоматической фиксации и возврат соединения в исходное состояние
//                connection.setAutoCommit(true);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Произошла ошибка при настройке транзакции для удаления старой таблицы");
//        }
//    }
//
//    // добавление в таблицу
//    public void saveUser(String name, String lastName, byte age) {
//        try {
//            connection.setAutoCommit(false); // Начало транзакции
//
//            try (PreparedStatement pstm = connection.prepareStatement("INSERT INTO usersTable (name, last_name, age) VALUES (?, ?, ?)")) {
//                pstm.setString(1, name);
//                pstm.setString(2, lastName);
//                pstm.setByte(3, age);
//                pstm.executeUpdate();
//                System.out.println("Добавлен пользователь:" + name);
//
//                connection.commit(); // Фиксация транзакции при успешной операции
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.err.println("Не добавлен пользователь:" + name);
//
//                connection.rollback(); // Откат транзакции в случае ошибки
//            } finally {
//                connection.setAutoCommit(true); // Включение автоматической фиксации обратно
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Произошла ошибка при настройке транзакции для добавления пользователя");
//        }
//    }
//
//    // удаление user(s) из таблицы
//    public void removeUserById(long id) {
//        try {
//            connection.setAutoCommit(false); // Начало транзакции
//
//            try (PreparedStatement pstm = connection.prepareStatement("DELETE FROM usersTable WHERE id = ?")) {
//                pstm.setLong(1, id);
//                pstm.executeUpdate();
//                System.out.println("Добавлен пользователь:");
//
//                connection.commit(); // Фиксация транзакции при успешной операции
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.err.println("Ошибка при удалении пользователя");
//
//                connection.rollback(); // Откат транзакции в случае ошибки
//            } finally {
//                connection.setAutoCommit(true); // Включение автоматической фиксации обратно
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Произошла ошибка при настройке транзакции для удаления пользователя из таблицы");
//        }
//    }
//
//
//    // получить всех user(s) из таблицы
//    public List<User> getAllUsers() throws SQLException {
//        List<User> userList = new ArrayList<>();
//
//        try {
//            connection.setAutoCommit(false); // Начало транзакции
//
//            String sql = "SELECT * FROM usersTable";
//
//            try (Statement statement = connection.createStatement();
//                 ResultSet resultSet = statement.executeQuery(sql)) {
//
//                while (resultSet.next()) {
//                    User user = new User();
//                    user.setId(resultSet.getLong("id"));
//                    user.setName(resultSet.getString("name"));
//                    user.setLastName(resultSet.getString("last_name"));
//                    user.setAge(resultSet.getByte("age"));
//
//                    userList.add(user);
//                    System.out.println("Получены пользователи");
//                }
//
//                connection.commit(); // Фиксация транзакции при успешном завершении операции
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.err.println("Ошибка при получении пользователей");
//
//                connection.rollback(); // Откат транзакции в случае ошибки
//            } finally {
//                connection.setAutoCommit(true); // Включение автоматической фиксации обратно
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Произошла ошибка при настройке транзакции для получения всех пользователей из таблицы");
//        }
//
//        return userList;
//    }
//
//    // удаление значений в таблице
//    public void cleanUsersTable() {
//        try {
//            connection.setAutoCommit(false); // Начало транзакции
//
//            try (Statement statement = connection.createStatement()) {
//                statement.executeUpdate("TRUNCATE TABLE usersTable");
//                System.out.println("Теперь таблица пустая");
//
//                connection.commit(); // Фиксация транзакции при успешной операции
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.err.println("Ошибка при удалении значений из таблицы");
//
//                connection.rollback(); // Откат транзакции в случае ошибки
//            } finally {
//                connection.setAutoCommit(true); // Включение автоматической фиксации обратно
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Произошла ошибка при настройке транзакции для удаления таблицы");
//        }
//    }
//}
