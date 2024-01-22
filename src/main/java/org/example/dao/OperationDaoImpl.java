package org.example.dao;

import org.example.exception.DataProcessingException;
import org.example.library.Dao;
import org.example.model.Account;
import org.example.model.Operation;
import org.example.model.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Dao
public class OperationDaoImpl implements OperationDao {

    @Override
    public Operation add(Operation operation) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(operation);
            transaction.commit();
            return operation;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add operation into DB: "
                    + operation, e);
        } finally {
            if (session != null) {
                session.clear();
            }
        }
    }

    @Override
    public void update(Operation operation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(operation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update current operation: " + operation, e);
        }
    }

    @Override
    public void delete(Operation operation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(operation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete current operation: "
                    + operation, e);
        }
    }

    @Override
    public Optional<Operation> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Operation> query = session.createQuery("Operation o"
                    + "WHERE o.id = :id", Operation.class);
            query.setParameter("id", id);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find operation by id: " + id, e);
        }
    }

    @Override
    public List<Operation> findOperationsByAccount(Account account) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Operation o LEFT JOIN FETCH o.account WHERE o.account = :account";
            return session.createQuery(hql, Operation.class)
                    .setParameter("account", account)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error while fetching operations by account", e);
        }
    }


    @Override
    public List<Operation> findOperationsByUserAndPeriod(User user, LocalDate fromDate, LocalDate toDate) {
        String hql = "FROM Operation WHERE account.user = :user "
                + "AND createdAt BETWEEN :fromDate AND :toDate";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Operation> query = session.createQuery(hql, Operation.class);
            query.setParameter("user", user)
                    .setParameter("fromDate", fromDate.atStartOfDay())
                    .setParameter("toDate", toDate.plusDays(1).atStartOfDay());
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error while fetching operations by user and period", e);
        }
    }
}
