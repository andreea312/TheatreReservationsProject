package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.service.IService;

import java.io.IOException;

public class InitialController {
    IService service;
    @FXML
    Button client;
    @FXML
    Button admin;
    @FXML
    Label whoLabel;

    public void setService(IService service){
        this.service = service;
    }

    @FXML
    protected void adminClicked() {
        Stage toBeClosed = (Stage) admin.getScene().getWindow();
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

    @FXML
    protected void clientClicked() {
        Stage toBeClosed = (Stage) client.getScene().getWindow();
        toBeClosed.close();

        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("clientMain-view.fxml"));
        try {
            scene = new Scene(loader.load(), 550, 650);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientMainController ctrl = loader.getController();
        ctrl.setService(service);
        ctrl.initialization();
        stage.setScene(scene);
        stage.show();
    }
}
