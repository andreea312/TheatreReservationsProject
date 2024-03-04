package org.example.service;

import org.example.domain.Client;
import org.example.domain.Reservation;

public interface IObserver {
    void reservationAdded(Reservation r);
    void reservationRemoved(Reservation r);
}
