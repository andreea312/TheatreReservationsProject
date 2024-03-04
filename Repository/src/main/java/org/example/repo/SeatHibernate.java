package org.example.repo;

import org.example.domain.Seat;
import org.example.domain.Show;
import org.example.repo.exceptions.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SeatHibernate implements SeatRepository{
    @Override
    public void add(Seat entity) {

    }

    @Override
    public void remove(Seat entity) {

    }

    @Override
    public void update(Seat entity) {
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
    public Seat getById(int id) {
        return null;
    }

    @Override
    public Iterable<Seat> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Seat> o = session.createQuery("from Seat", Seat.class).list();
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
    public Seat getByLodgeRowNumber(int lodge, int row, int number) {
        Seat o = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                o = session.createQuery("from Seat where lodge=:lodge and row=:row and number=:number", Seat.class)
                        .setParameter("lodge", lodge)
                        .setParameter("row", row)
                        .setParameter("number", number)
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
}
