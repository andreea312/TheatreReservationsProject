package org.example.repo;

import org.example.domain.Client;
import org.example.domain.Reservation;
import org.example.domain.Seat;
import org.example.domain.Show;
import org.example.repo.exceptions.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ReservationHibernate implements ReservationRepository{
    @Override
    public void add(Reservation entity) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                //session.persist(entity);
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public void remove(Reservation entity) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            try{
                session.beginTransaction();
                    Reservation o = session.createQuery("from Reservation where id=:id", Reservation.class)
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
    public void update(Reservation entity) {

    }

    @Override
    public Reservation getById(int id) {
        return null;
    }

    @Override
    public Iterable<Reservation> getAll() {
        return null;
    }

    @Override
    public Reservation getReservation(Client client, Show show, Seat seat) {
        Reservation o = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                o = session.createQuery("from Reservation where client=:client and show=:show and seat=:seat", Reservation.class)
                        .setParameter("client", client)
                        .setParameter("show", show)
                        .setParameter("seat", seat)
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
    public Iterable<Reservation> getReservationsByShow(Show show) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List<Reservation> o = session.createQuery("from Reservation where show=:show", Reservation.class)
                        .setParameter("show", show).list();
                tx.commit();
                return o;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }
}
