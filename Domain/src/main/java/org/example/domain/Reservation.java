package org.example.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;

@javax.persistence.Entity
@Table(name = "Reservation")
public class Reservation extends Entity<Integer>{
    private Client client;
    private Show show;
    private Seat seat;

    public Reservation(){}

    public Reservation(Client client, Show show, Seat seat) {
        super();
        this.client = client;
        this.show = show;
        this.seat = seat;
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public Integer getId(){ return super.getId();}

    @Override
    public void setId(Integer id){ super.setId(id);}

    @ManyToOne()
    @JoinColumn(name = "client", referencedColumnName = "id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne()
    @JoinColumn(name = "show", referencedColumnName = "id")
    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", idKid = " + client.getId() +
                ", idRace = " + show.getId() +
                ", seat = " + seat +
                '}';
    }

    @ManyToOne()
    @JoinColumn(name = "seat", referencedColumnName = "id")
    public Seat getSeat() {
        return seat;
    }
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
