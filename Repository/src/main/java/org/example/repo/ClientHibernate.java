package org.example.repo;

import org.example.domain.Admin;
import org.example.domain.Client;
import org.example.repo.exceptions.RepositoryException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClientHibernate implements ClientRepository{
    @Override
    public void add(Client entity) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void remove(Client entity) {

    }

    @Override
    public void update(Client entity) {
    }

    @Override
    public Client getById(int id) {
        return null;
    }

    @Override
    public Iterable<Client> getAll() {
        return null;
    }

    @Override
    public Client getClient(String firstName, String lastName, String phone, String email) {
        Client o = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                o = session.createQuery("from Client where firstName=:firstName and lastName=:lastName and phone=:phone and email=:email", Client.class)
                        .setParameter("firstName", firstName)
                        .setParameter("lastName", lastName)
                        .setParameter("phone", phone)
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
}
