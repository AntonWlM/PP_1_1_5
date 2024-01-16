package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDaoHibernateImpl userService;

    public UserServiceImpl() {
        userService = new UserDaoHibernateImpl();
    }

    @Override
    public void createUsersTable() {
        try {
            userService.createUsersTable();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid createUsersTable: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
       try {
        userService.dropUsersTable();
         } catch (Exception e) {
        throw new IllegalStateException("Invalid dropUsersTable: " + e.getMessage());
        }
    }
    

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            userService.saveUser(name, lastName, age);
        } catch (Exception e) {
        throw new IllegalStateException("Invalid saveUser: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            userService.removeUserById(id);
        } catch (Exception e) {
        throw new IllegalStateException("Invalid removeUserById: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
        List<User> list = userService.getAllUsers();
        if (list == null) {
            System.out.println("Ошибка получения списка Users");
        }
        return list;
        } catch (Exception e) {
            throw new IllegalStateException("Invalid getAllUsers: " + e.getMessage());
        }
    }

    @Override
    public void cleanUsersTable() {
        try {
        userService.cleanUsersTable();
        } catch (Exception e) {
        throw new IllegalStateException("Invalid cleanUsersTable: " + e.getMessage());
        }
    }
}
