package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String createUsersQuery = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
            "age TINYINT NOT NULL)";

    private static final String dropUsersQuery = "DROP TABLE IF EXISTS users";


    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() throws SQLException {
            sessionFactory = new Util().getSessionFactory();
    }



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
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
                throw new IllegalStateException("Invalid removeUserById: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("from User").list();
            return  users;
        } catch (Exception e) {
            throw new IllegalStateException("Invalid getAllUsers: " + e.getMessage());
        }
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
