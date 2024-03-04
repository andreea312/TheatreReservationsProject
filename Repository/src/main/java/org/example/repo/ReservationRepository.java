package org.example.repo;

import org.example.domain.Client;
import org.example.domain.Reservation;
import org.example.domain.Seat;
import org.example.domain.Show;

public interface ReservationRepository extends Repository<Integer, Reservation>{
    Reservation getReservation(Client client, Show show, Seat seat);
    Iterable<Reservation> getReservationsByShow(Show show);
}
