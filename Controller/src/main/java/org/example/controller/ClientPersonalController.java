package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.domain.Client;
import org.example.domain.Reservation;
import org.example.domain.Seat;
import org.example.domain.Show;
import org.example.service.Exceptionn;
import org.example.service.IObserver;
import org.example.service.IService;

import java.util.Set;

public class ClientPersonalController implements IObserver {
    ClientMainController clientMainController;
    IService service;
    Set<Seat> selectedSeats;
    Show selectedShow;
    Client client;
    @FXML
    Label personalDataLabel;
    @FXML
    Button confirmReservationButton;
    @FXML
    Button cancelReservationButton;
    @FXML
    TextArea infoField;
    @FXML
    Label firstNameLabel;
    @FXML
    Label lastNameLabel;
    @FXML
    Label phoneLabel;
    @FXML
    Label emailLabel;
    @FXML
    TextField firstNameText;
    @FXML
    TextField lastNameText;
    @FXML
    TextField phoneText;
    @FXML
    TextField emailText;

    public void setClientMainController(ClientMainController clientMainController){
        this.clientMainController = clientMainController;
    }

    public void setService(IService service) {
        this.service = service;
        cancelReservationButton.setDisable(true);
    }

    public void setSelectedShow(Show selectedShow) {
        this.selectedShow = selectedShow;
    }

    public void setSelectedSeats(Set<Seat> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public void confirmReservationButtonClicked() {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String phone = phoneText.getText();
        String email = emailText.getText();

        try {
            client = new Client(firstName, lastName, phone, email);
            StringBuilder strSeats = new StringBuilder();
            float totalPrice = (float) 0;
            for(Seat s: selectedSeats){
                service.addReservation(new Reservation(client, selectedShow, s), this);
                strSeats.append("Lodge ").append(s.getLodge()).append(", Row ").append(s.getRow()).append(", Number ").append(s.getNumber()).append("\n");
                totalPrice += s.getPrice();
            }

            infoField.setText("                                                 THANK YOU, " + firstName + "!" + "\n\n" +
                    "SHOW:  " + selectedShow.getName() + " - " + selectedShow.getDescription() + "\n" +
                    "DATE:  " + selectedShow.getData() + "\n\n" +
                    "SEAT: " + strSeats +
                    "PRICE: " + totalPrice);
            confirmReservationButton.setDisable(true);
            cancelReservationButton.setDisable(false);


        } catch (Exceptionn e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Reservation failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            confirmReservationButton.setDisable(false);
            cancelReservationButton.setDisable(true);
        }
    }

    public void cancelReservationButtonClicked(){
        firstNameText.clear();
        lastNameText.clear();
        phoneText.clear();
        emailText.clear();
        infoField.clear();
        confirmReservationButton.setDisable(false);
        cancelReservationButton.setDisable(true);
        try {
            for(Seat s: selectedSeats){
                service.removeReservation(new Reservation(client, selectedShow, s), this);
            }
        }
        catch(Exceptionn e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Canceling failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            confirmReservationButton.setDisable(false);
            cancelReservationButton.setDisable(true);
        }
    }

    @Override
    public void reservationAdded(Reservation r) {
        clientMainController.reservationAdded(r);
    }

    @Override
    public void reservationRemoved(Reservation r) {
        clientMainController.reservationRemoved(r);
    }
}
