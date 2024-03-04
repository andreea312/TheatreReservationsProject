package org.example.controller;

import org.example.domain.Admin;
import org.example.service.Exceptionn;
import org.example.service.IService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController{
    IService service;
    @FXML
    PasswordField passwordField;
    @FXML
    Label loginLabel;
    @FXML
    Label emailLabel;
    @FXML
    Label passwordLabel;
    @FXML
    Button loginButton;
    @FXML
    TextField emailTextField;
    @FXML
    Hyperlink registerHyperlink;

    public void setService(IService service) {
        this.service = service;
    }

    @FXML
    protected void loginButtonClicked() {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        emailTextField.clear();
        passwordField.clear();
        Admin admin = null;
        try {
            Stage stage = new Stage();
            Scene scene;
            FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("adminMain-view.fxml"));
            try {
                scene = new Scene(loader.load(), 860, 500);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            AdminMainController ctrl = loader.getController();
            ctrl.setService(service);
            admin = service.getByMailPassword(email, password);
            ctrl.setAdmin(admin);
            stage.setScene(scene);
            stage.setTitle(admin.getEmail());
            ctrl.initialization();
            stage.show();
            Stage toBeClosed = (Stage) loginButton.getScene().getWindow();
            toBeClosed.close();
        } catch (Exceptionn e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    protected void registerHyperlinkClicked(){
        Stage toBeClosed = (Stage) registerHyperlink.getScene().getWindow();
        toBeClosed.close();

        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("register-view.fxml"));
        try {
            scene = new Scene(loader.load(), 300, 300);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RegisterController ctrl = loader.getController();
        ctrl.setService(service);
        stage.setScene(scene);
        stage.show();
    }
}