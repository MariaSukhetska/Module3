package org.example.dao;

import org.example.exception.DataProcessingException;
import org.example.libruary.Dao;
import org.example.model.Account;
import org.example.model.Operation;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Dao
public class AccountDaoImpl implements AccountDao {

    @Override
    public List<Operation> findOperationsInPeriod
            (Account account, LocalDate fromDate, LocalDate toDate) {
        String hql = "FROM Operation WHERE account = :account AND createdAt BETWEEN "
                + ":fromDate AND :toDate";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Operation> query = session.createQuery(hql, Operation.class);
            query.setParameter("account", account)
                    .setParameter("fromDate", fromDate.atStartOfDay())
                    .setParameter("toDate", toDate.plusDays(1).atStartOfDay());
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error while fetching operations in period", e);
        }
    }

    @Override
    public Account add(Account account) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(account);
            transaction.commit();
            return account;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add account: " + account, e);
        } finally {
            if (session != null) {
                session.clear();
            }
        }
    }

    @Override
    public void update(Account account) {
     Transaction transaction = null;
     try(Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();
         session.merge(account);
         transaction.commit();
     } catch (Exception e) {
         if (transaction != null) {
             transaction.rollback();
         }
         throw new DataProcessingException("Can't update current account: " + account, e);
     }
    }

    @Override
    public void delete(Account account) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete current account: " + account, e);
        }
    }

    @Override
    public Optional<Account> findById(Long id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Account> query = session.createQuery("Account a "
                    + "WHERE a.id = :id", Account.class);
            query.setParameter("id", id);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find account by id: " + id, e);
        }
    }
}
