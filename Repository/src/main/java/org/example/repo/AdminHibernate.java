package org.example.repo;
import org.example.domain.Admin;
import org.example.repo.exceptions.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdminHibernate implements AdminRepository {

    @Override
    public Admin getByMailPassword(String email, String password) {
        Admin o = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                o = session.createQuery("from Admin where email=:email and password=:password", Admin.class)
                        .setParameter("email", email)
                        .setParameter("password", password)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println(o);
                tx.commit();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        }
        return o;
    }

    @Override
    public Admin getByMail(String email) {
        Admin o = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                o = session.createQuery("from Admin where email=:email", Admin.class)
                        .setParameter("email", email)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println(o);
                tx.commit();
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
            }
        }
        return o;
    }

    @Override
    public void add(Admin entity) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.persist(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Iterable<Admin> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Admin> o = session.createQuery("from Admin", Admin.class).list();
                tx.commit();
                return o;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

    @Override
    public void remove(Admin entity) {
    }

    @Override
    public void update(Admin entity) {
    }

    @Override
    public Admin getById(int id) {
        return null;
    }

}
