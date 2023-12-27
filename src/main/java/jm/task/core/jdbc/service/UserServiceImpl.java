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
        userService.createUsersTable();
        System.out.println("Таблица Users создана");
    }

    @Override
    public void dropUsersTable() {
        //todo: codeStyle
        userService.dropUsersTable();
        System.out.println("Таблица Users удалена");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        userService.saveUser(name, lastName, age);
        System.out.println("User: " + name + " добавлен");
    }

    @Override
    public void removeUserById(long id) {

        userService.removeUserById(id);
        System.out.println("User с Id = " + id + " удален");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = userService.getAllUsers();
        if (list != null) {
            System.out.println("Список Users успешно получен");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        userService.cleanUsersTable();
        System.out.println("Таблица Users очищена");
    }
}
