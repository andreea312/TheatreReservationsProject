package org.example.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.domain.Admin;
import org.example.domain.Client;
import org.example.domain.validators.AdminValidator;
import org.example.domain.validators.ClientValidator;
import org.example.domain.validators.Validator;
import org.example.repo.*;
import org.example.service.IObserver;
import org.example.service.IService;
import org.example.service.Service;

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Validator<Client> cv = new ClientValidator();
        Validator<Admin> av = new AdminValidator();
        AdminRepository ar = new AdminHibernate();
        ShowRepository shr = new ShowHibernate();
        SeatRepository str = new SeatHibernate();
        ClientRepository cr = new ClientHibernate();
        ReservationRepository rr = new ReservationHibernate();
        IService srv = new Service(cv, av, ar, shr, str, cr, rr);

        Scene scene;
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("initial-view.fxml"));
        try {
            scene = new Scene(loader.load(), 550, 650);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InitialController ctrl = loader.getController();
        ctrl.setService(srv);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
