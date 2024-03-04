package org.example.repo;

import org.example.domain.Show;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ShowHibernate implements ShowRepository{
    @Override
    public void add(Show entity) {
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
    public void remove(Show entity) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            try{
                session.beginTransaction();
                Show o = session.createQuery("from Show where id=:id", Show.class)
                        .setParameter("id", entity.getId())
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(o);
                session.getTransaction().commit();
            }catch (RuntimeException e){
                if(session.getTransaction() != null){
                    session.getTransaction().rollback();
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void update(Show entity) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            try{
                session.beginTransaction();
                session.update(entity);
                session.getTransaction().commit();
            }catch (RuntimeException ex){
                session.getTransaction().rollback();
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    public Show getById(int id) {
        return null;
    }

    @Override
    public Iterable<Show> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Show> o = session.createQuery("from Show", Show.class).list();
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
    public Show getByDate(LocalDate data) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Show o = session.createQuery("from Show where data=:data", Show.class)
                        .setParameter("data", data)
                        .setMaxResults(1)
                        .uniqueResult();
                o.setData(data);
                tx.commit();
                return o;
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }
}
