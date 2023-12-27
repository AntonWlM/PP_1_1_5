package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    //todo: codeStyle для читаемости кода - строки стоит разделять
    public UserDaoHibernateImpl() {
        //todo: codeStyle     SessionFactory sessionFactory = ..........Util.getSessionFactory(); - заводим переменную через конструктор
    }

    private static final String createUsersQuery = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
            "age TINYINT NOT NULL)";

    private static final String dropUsersQuery = "DROP TABLE IF EXISTS users";

    private Transaction transaction = null;//todo: не стоит - много пользователей (многопоточность) делают вынос этого поля bug-ным. Нужно занести в методы


    @Override
    public void createUsersTable() {
       try (Session session = sessionFactory.openSession()) {//todo: ...
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(createUsersQuery).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                throw new IllegalStateException("Invalid createUsersQuery: " + e.getMessage());
            }
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(dropUsersQuery).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                throw new IllegalStateException("Invalid dropUsersTable: " + e.getMessage());
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                throw new IllegalStateException("Invalid saveUser: " + e.getMessage());
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        User user;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                throw new IllegalStateException("Invalid removeUserById: " + e.getMessage());
            }
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();//todo: из теории вопроса - transaction нужны для того, чтобы?? Здесь например, вполне, можем обойтись
            users = session.createQuery("from User").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                throw new IllegalStateException("Invalid getAllUsers: " + e.getMessage());
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                throw new IllegalStateException("Invalid cleanUsersTable: " + e.getMessage());
            }
        }
    }
}
