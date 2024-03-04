package org.example.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.domain.Reservation;
import org.example.domain.Seat;
import org.example.domain.Show;
import org.example.service.Exceptionn;
import org.example.service.IObserver;
import org.example.service.IService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ClientMainController implements IObserver {
    IService service;
    @FXML
    TableView<Show> showsTable;
    @FXML
    Label chooseLabel;
    @FXML
    Label sceneLabel;
    @FXML
    Label rightEntranceLabel;
    @FXML
    Label leftEntranceLabel;
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
    GridPane gridPane;
    @FXML
    Button makeReservationButton;
    @FXML
    ObservableList<Show> list1 = FXCollections.observableArrayList();
    Set<Seat> selectedSeats = new HashSet<>();
    Show selectedShow;
    @FXML
    TextArea seatsText;
    String str;
    @FXML
    ObservableList<ToggleButton> buttons = FXCollections.observableArrayList();

    public void setService(IService service){
        this.service = service;
        this.selectedShow = null;
        gridPane.setVisible(false);
        seatsText.setDisable(true);
        seatsText.setVisible(false);
        sceneLabel.setVisible(false);
        rightEntranceLabel.setVisible(false);
        leftEntranceLabel.setVisible(false);
        makeReservationButton.setDisable(true);
    }

    public void initialization(){
        populateShowsTable();
        Platform.runLater(()->{
            populateGridView();
        });

    }

    public void populateShowsTable(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timeStart"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("timeEnd"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        list1.clear();
        try {
            for (Show s : service.getAllShows()){
                System.out.println(s);
                list1.add(s);
            }
        } catch (Exceptionn e) {
            throw new RuntimeException(e);
        }
        showsTable.setItems(list1);

        showsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (oldSelection != null) {
                selectedShow = null;
                gridPane.setVisible(false);
                seatsText.setVisible(false);
                sceneLabel.setVisible(false);
                rightEntranceLabel.setVisible(false);
                leftEntranceLabel.setVisible(false);
            }
            if (newSelection != null) {
                Show selected = showsTable.getSelectionModel().getSelectedItem();
                System.out.println(selected.getData());
                System.out.println(LocalDate.now());
                if (Objects.equals(selected.getData(), LocalDate.now())) {
                    selectedShow = selected;
                    gridPane.setVisible(true);
                    sceneLabel.setVisible(true);
                    rightEntranceLabel.setVisible(true);
                    leftEntranceLabel.setVisible(true);
                    seatsText.clear();
                    selectedSeats.clear();
                    makeReservationButton.setDisable(true);
                    str = "";
                }
            }
        });
    }
    public void populateGridView(){
        gridPane.setPadding(new Insets(0,0,0,0));
        gridPane.setHgap(2);
        gridPane.setVgap(10);

        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 9; c++) {
                String value;
                if (r <= 1){value = 'A' + String.valueOf(r) + String.valueOf(c);}
                else if (r <= 3){value = 'B' + String.valueOf(r) + String.valueOf(c);}
                else {value = 'C' + String.valueOf(r) + String.valueOf(c);}

                ToggleButton button = new ToggleButton(value);
                buttons.add(button);
                gridPane.add(button, c, r);

                int lodge = 0;
                if(button.getText().charAt(0)=='A'){
                    lodge = 1;
                }
                else if(button.getText().charAt(0)=='B'){
                    lodge = 2;
                }
                else if(button.getText().charAt(0)=='C'){
                    lodge = 3;
                }
                System.out.println("lodge "+ lodge);
                System.out.println("row "+ r + " " + (Character.getNumericValue(button.getText().charAt(1))));
                System.out.println("nuumber "+ c + " " + (Character.getNumericValue(button.getText().charAt(2))));
                try {
                    Seat seat = service.getByLodgeRowNumber(lodge, r, c);
                    if (!seat.getAvailable()){
                        button.setDisable(true);
                    }
                } catch (Exceptionn ex) {
                    throw new RuntimeException(ex);
                }

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        int lodge = 0;
                        if(button.getText().charAt(0)=='A'){
                            lodge = 1;
                        }
                        else if(button.getText().charAt(0)=='B'){
                            lodge = 2;
                        }
                        else if(button.getText().charAt(0)=='C'){
                            lodge = 3;
                        }
                        try {
                            Seat selectedSeat = service.getByLodgeRowNumber(lodge,
                                    (Character.getNumericValue(button.getText().charAt(1))),
                                    (Character.getNumericValue(button.getText().charAt(2))));
                            if (!selectedSeats.contains(selectedSeat)){
                                selectedSeats.add(selectedSeat);
                                String txt = selectedSeat.toString() + "\n";
                                str += txt;
                                seatsText.setText(str);
                                seatsText.setVisible(true);
                                makeReservationButton.setDisable(false);
                            }
                        } catch (Exceptionn ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        }
    }

    public void makeReservationButtonClicked(){
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("clientPersonal-view.fxml"));
        try {
            scene = new Scene(loader.load(), 550, 650);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientPersonalController ctrl = loader.getController();
        ctrl.setService(service);
        ctrl.setClientMainController(this);
        ctrl.setSelectedShow(selectedShow);
        ctrl.setSelectedSeats(selectedSeats);
        stage.setScene(scene);
        stage.show();
        seatsText.clear();
        makeReservationButton.setDisable(true);
        str = "";
    }

    @Override
    public void reservationAdded(Reservation r) {
        if(r.getShow().equals(selectedShow)){
            Platform.runLater(()->{ buttons.get(r.getSeat().getId()-1).setDisable(true); });
        }
    }

    @Override
    public void reservationRemoved(Reservation r) {
        if(r.getShow().equals(selectedShow)){
            Platform.runLater(()->{ buttons.get(r.getSeat().getId()-1).setDisable(false); });
        }
    }
}
