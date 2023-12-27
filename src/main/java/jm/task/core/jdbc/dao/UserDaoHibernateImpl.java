package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
        try {
            sessionFactory = new Util().getSessionFactory();
        } catch (Exception e) {
            System.out.println("ошибка создания SessionFactory");
        }
    }


    private static final String createUsersQuery = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
            "age TINYINT NOT NULL)";

    private static final String dropUsersQuery = "DROP TABLE IF EXISTS users";

    @Override
    public void createUsersTable() {
       try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(createUsersQuery).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
           throw new IllegalStateException("Invalid createUsersQuery: " + e.getMessage());
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(dropUsersQuery).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
                throw new IllegalStateException("Invalid dropUsersTable: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
                throw new IllegalStateException("Invalid saveUser: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        User user;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
                throw new IllegalStateException("Invalid removeUserById: " + e.getMessage());
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("from User").list();
        } catch (Exception e) {
                throw new IllegalStateException("Invalid getAllUsers: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
                throw new IllegalStateException("Invalid cleanUsersTable: " + e.getMessage());
        }
    }
}
