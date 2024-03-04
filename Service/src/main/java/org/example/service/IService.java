package org.example.service;

import org.example.domain.*;
import org.example.domain.validators.ValidationException;

import java.time.LocalDate;

public interface IService {
    Admin getByMailPassword(String email, String password) throws Exceptionn;
    Admin getByMail(String email) throws Exceptionn;
    void addAdmin(Admin e) throws ValidationException, Exceptionn;
    void addClient(Client e) throws ValidationException, Exceptionn;
    void addReservation(Reservation e, IObserver observer) throws Exceptionn;
    void removeReservation(Reservation e, IObserver observer) throws Exceptionn;
    void addShow(Show e) throws Exceptionn;
    void removeShow(Show e, IObserver observer) throws Exceptionn;
    void updateShow(Show e) throws Exceptionn;
    Show getShowByDate(LocalDate date) throws Exceptionn;
    Iterable<Show> getAllShows() throws Exceptionn;
    Seat getByLodgeRowNumber(int lodge, int row, int number) throws Exceptionn;
    Iterable<Reservation> getReservationsByShow(Show show) throws Exceptionn;
    void refreshSeats() throws Exceptionn;
}
