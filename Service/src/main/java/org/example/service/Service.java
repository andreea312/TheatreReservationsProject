package org.example.service;

import org.example.domain.*;
import org.example.domain.validators.ValidationException;
import org.example.domain.validators.Validator;
import org.example.repo.*;

import java.time.LocalDate;
import java.util.Objects;

public class Service implements IService {
    private Validator<Client> clientValidator;
    private Validator<Admin> adminValidator;
    private AdminRepository adminRepo;
    private ShowRepository showRepo;
    private SeatRepository seatRepo;
    private ClientRepository clientRepo;
    private ReservationRepository reservationRepo;

    public Service(Validator<Client> clientValidator, Validator<Admin> adminValidator, AdminRepository adminRepo,  ShowRepository showRepo, SeatRepository seatRepo, ClientRepository clientRepo, ReservationRepository reservationRepo) {
        this.clientValidator = clientValidator;
        this.adminValidator = adminValidator;
        this.adminRepo = adminRepo;
        this.showRepo = showRepo;
        this.seatRepo = seatRepo;
        this.clientRepo = clientRepo;
        this.reservationRepo = reservationRepo;
    }

    @Override
    public Admin getByMailPassword(String email, String password) throws Exceptionn {
        var admin = adminRepo.getByMailPassword(email, password);
        if(admin==null)
            throw new Exceptionn("Invalid admin credentials");
        return admin;
    }

    @Override
    public Admin getByMail(String email){
        return adminRepo.getByMail(email);
    }


    public void addAdmin(Admin admin) throws ValidationException {
        Admin a = adminRepo.getByMail(admin.getEmail());
        if (a == null)
        {
            try{
                adminValidator.validate(admin);
                adminRepo.add(admin);
            }
            catch (ValidationException ex) {
                throw new ValidationException(ex.getMessage());
            }
        }
        else{
            throw new ValidationException("Cannot create two accounts with the same email address!");
        }
    }

    @Override
    public void addClient(Client cl) throws ValidationException {
        try{
            Client c = clientRepo.getClient(cl.getFirstName(), cl.getLastName(), cl.getPhone(), cl.getEmail());
            if (c==null) {
                clientValidator.validate(cl);
                clientRepo.add(cl);
            }
            else{
                cl.setId(c.getId());
            }
        }
        catch (ValidationException ex) {
            throw new ValidationException(ex.getMessage());
        }
    }

    @Override
    public synchronized void addReservation(Reservation e, IObserver observer) throws Exceptionn {
        if(reservationRepo.getReservation(e.getClient(), e.getShow(), e.getSeat()) != null){
            throw new Exceptionn("Reservation already exists!");
        }
        else if(e.getSeat().getAvailable()) {
            try {
                addClient(e.getClient());
                reservationRepo.add(e);
                Seat newSeat = new Seat(e.getSeat().getNumber(), e.getSeat().getRow(), e.getSeat().getLodge(), e.getSeat().getPrice(), false);
                newSeat.setId(e.getSeat().getId());
                seatRepo.update(newSeat);

                observer.reservationAdded(e);

            } catch (ValidationException ex) {
                throw new Exceptionn(ex.getMessage());
            }
        }
        else{
            throw new Exceptionn("Seat not available!");
        }
    }

    @Override
    public synchronized void removeReservation(Reservation e, IObserver observer) throws Exceptionn {
        Client c1 = e.getClient();
        Client c2 = clientRepo.getClient(c1.getFirstName(), c1.getLastName(), c1.getPhone(), c1.getEmail());
        c1.setId(c2.getId());

        Reservation r = reservationRepo.getReservation(c1, e.getShow(), e.getSeat());
        if (r==null) {
            throw new Exceptionn("Reservation does not exist!");
        }
        else {
            e.setId(r.getId());
            reservationRepo.remove(e);
            Seat newSeat = new Seat(e.getSeat().getNumber(), e.getSeat().getRow(), e.getSeat().getLodge(), e.getSeat().getPrice(), true);
            newSeat.setId(e.getSeat().getId());
            seatRepo.update(newSeat);

            observer.reservationRemoved(e);
        }
    }

    @Override
    public void addShow(Show e) throws Exceptionn {
        boolean ok = true;
        for(Show s: showRepo.getAll()){
            if (s.getData().equals(e.getData())) {
                ok = false;
                break;
            }
        }
        if(ok) {
            System.out.println("service adding show " + e.getId() + ", " + e);
            showRepo.add(e);
        }
        else{
            throw new Exceptionn("Can't schedule two shows for the same day!");
        }
    }

    @Override
    public void removeShow(Show e, IObserver observer) throws Exceptionn {
        for (Reservation r: getReservationsByShow(e)){
            removeReservation(r, observer);
        }
        showRepo.remove(e);
    }

    @Override
    public void updateShow(Show e) throws Exceptionn {
        boolean ok = true;
        for(Show s: showRepo.getAll()){
            if(s.getData().equals(e.getData()) && !Objects.equals(s.getId(), e.getId())) {
                ok = false;
            }
        }
        if(ok) {
            System.out.println("service updating show " + e.getId() + ", " + e);
            showRepo.update(e);
        }
        else{
            throw new Exceptionn("Can't schedule two shows for the same day!");
        }
    }

    @Override
    public Show getShowByDate(LocalDate date) {
        return showRepo.getByDate(date);
    }

    @Override
    public Iterable<Show> getAllShows() {
        return showRepo.getAll();
    }

    @Override
    public Seat getByLodgeRowNumber(int lodge, int row, int number){
        return seatRepo.getByLodgeRowNumber(lodge, row, number);
    }

    @Override
    public Iterable<Reservation> getReservationsByShow(Show show) throws Exceptionn{
        return reservationRepo.getReservationsByShow(show);
    }

    @Override
    public void refreshSeats() {
        System.out.println("REFRESHING IN SERVICE.............................");
        for(Seat s : seatRepo.getAll()){
            System.out.println(s.getId() + ": " + s);
            if(!s.getAvailable()) {
                Seat refreshedSeat = new Seat(s.getNumber(), s.getRow(), s.getLodge(), s.getPrice(), true);
                refreshedSeat.setId(s.getId());
                System.out.println(refreshedSeat + " " + refreshedSeat.getId());
                seatRepo.update(refreshedSeat);
            }
        }
    }
}
