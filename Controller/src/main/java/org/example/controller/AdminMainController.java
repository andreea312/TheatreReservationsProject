package org.example.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.example.domain.*;
import org.example.service.Exceptionn;
import org.example.service.IService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AdminMainController {
    @FXML
    Button logoutButton;
    IService service;
    Admin admin;
    @FXML
    TableView<Show> showsTable;
    @FXML
    TableColumn<Show, String> nameColumn;
    @FXML
    TableColumn<Show, String> dateColumn;
    @FXML
    TableColumn<Show, String> startTimeColumn;
    @FXML
    TableColumn<Show, String> endTimeColumn;
    @FXML
    TableColumn<Show, String> descriptionColumn;
    @FXML
    TableView<Reservation> reservationsTable;
    @FXML
    TableColumn<Reservation, String> emailColumn;
    @FXML
    TableColumn<Reservation, String> phoneColumn;
    @FXML
    TableColumn<Reservation, String> seatColumn;
    @FXML
    TableColumn<Reservation, String> priceColumn;
    @FXML
    Button addButton;
    @FXML
    Button removeButton;
    @FXML
    Button updateButton;
    @FXML
    Button refreshSeatsButton;
    @FXML
    TextField titleField;
    @FXML
    TextField timeStartField;
    @FXML
    TextField timeEndField;
    @FXML
    DatePicker datePicker;
    @FXML
    TextField descriptionField;
    @FXML
    ObservableList<Show> list1 = FXCollections.observableArrayList();
    @FXML
    ObservableList<Reservation> list2 = FXCollections.observableArrayList();
    Show selectedShow;

    public void setService(IService service) {
        this.service = service;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void initialization() {
        Platform.runLater(() -> {
            populateShowsTable();
            removeButton.setDisable(true);
            updateButton.setDisable(true);
        });
    }

    public void populateShowsTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timeStart"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timeEnd"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        list1.clear();
        try {
            for (Show s : service.getAllShows()) {
                System.out.println(s);
                list1.add(s);
            }
        } catch (Exceptionn e) {
            throw new RuntimeException(e);
        }
        showsTable.setItems(list1);

        showsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedShow = showsTable.getSelectionModel().getSelectedItem();
                titleField.setText(selectedShow.getName());
                datePicker.setValue(selectedShow.getData());
                timeStartField.setText(selectedShow.getTimeStart().toString());
                timeEndField.setText(selectedShow.getTimeEnd().toString());
                descriptionField.setText(selectedShow.getDescription());
                removeButton.setDisable(false);
                updateButton.setDisable(false);
                populateReservationsTable();
            }
        });
    }

    public void populateReservationsTable() {
        priceColumn.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> r) {
                        Float price = r.getValue().getSeat().getPrice();
                        return new SimpleStringProperty(String.valueOf(price));
                    }
                }
        );
        seatColumn.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> r) {
                        Seat seat = r.getValue().getSeat();
                        String lodge = String.valueOf(seat.getLodge());
                        String row = String.valueOf(seat.getRow());
                        String number = String.valueOf(seat.getNumber());
                        return new SimpleStringProperty(lodge + ", " + row + ", " + number);
                    }
                }
        );
        phoneColumn.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> r) {
                        String phone = r.getValue().getClient().getPhone();
                        return new SimpleStringProperty(phone);
                    }
                }
        );
        emailColumn.setCellValueFactory(
                new Callback<>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> r) {
                        String email = r.getValue().getClient().getEmail();
                        return new SimpleStringProperty(email);
                    }
                }
        );
        list2.clear();
        try {
            for (Reservation r : service.getReservationsByShow(selectedShow)) {
                System.out.println(r);
                list2.add(r);
            }
        } catch (Exceptionn e) {
            throw new RuntimeException(e);
        }
        reservationsTable.setItems(list2);
    }

    public void logoutButtonClicked() {
        Stage toBeClosed = (Stage) logoutButton.getScene().getWindow();
        toBeClosed.close();

        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("login-view.fxml"));
        try {
            scene = new Scene(loader.load(), 300, 300);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LoginController ctrl = loader.getController();
        ctrl.setService(service);
        stage.setScene(scene);
        stage.show();
    }

    public void addButtonClicked() {
        String name = titleField.getText();
        LocalDate data = datePicker.getValue();
        LocalTime timeStart = LocalTime.parse(timeStartField.getText());
        LocalTime timeEnd = LocalTime.parse(timeEndField.getText());
        String description = descriptionField.getText();
        try {
            Show s = new Show(name, data, timeStart, timeEnd, description);
            service.addShow(s);
            for (Show i : service.getAllShows()) {
                if (s.getData().equals(i.getData())) {
                    s.setId(i.getId());
                    break;
                }
            }
            list1.add(s);
            titleField.clear();
            datePicker.setValue(LocalDate.now());
            timeStartField.clear();
            timeEndField.clear();
            descriptionField.clear();
        } catch (Exceptionn e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Adding failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void removeButtonClicked() {
        try {
            service.removeShow(selectedShow, null);
            list1.remove(selectedShow);
            titleField.clear();
            datePicker.setValue(LocalDate.now());
            timeStartField.clear();
            timeEndField.clear();
            descriptionField.clear();
        } catch (Exceptionn e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Removal failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void updateButtonClicked() {
        String name = titleField.getText();
        LocalDate data = datePicker.getValue();
        LocalTime timeStart = LocalTime.parse(timeStartField.getText());
        LocalTime timeEnd = LocalTime.parse(timeEndField.getText());
        String description = descriptionField.getText();
        try {
            Show s = new Show(name, data, timeStart, timeEnd, description);
            s.setId(selectedShow.getId());
            service.updateShow(s);
            list1.remove(selectedShow);
            list1.add(s);
            titleField.clear();
            datePicker.setValue(LocalDate.now());
            timeStartField.clear();
            timeEndField.clear();
            descriptionField.clear();
        } catch (Exceptionn e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Updating failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void refreshSeatsButtonClicked() {
        try {
            service.refreshSeats();
        } catch (Exceptionn e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Refreshing failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
