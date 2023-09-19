package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();


        userService.createUsersTable();
        userService.saveUser("A", "AA", (byte) 11);
        userService.saveUser("B", "BB", (byte) 22);
        userService.saveUser("C", "CC", (byte) 33);
        userService.saveUser("D", "DD", (byte) 44);

        //получение всех пользователей и передача в консоль
        List<User> userList = userService.getAllUsers();
        userList.forEach(System.out::println);

        // очищение таблицы
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
