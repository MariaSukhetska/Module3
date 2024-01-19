package org.example.dao;

import org.example.exception.DataProcessingException;
import org.example.libruary.Dao;
import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao {

    @Override
    public User add(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert user into DB: " + user, e);
        } finally {
            if (session != null) {
                session.clear();
            }
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
             transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update current user: " + user, e);
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete current user: " + user, e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("From User u"
            + "WHERE u.id = :id", User.class);
            query.setParameter("id", id);
           return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find user by id: " + id, e);
        }
    }
}
