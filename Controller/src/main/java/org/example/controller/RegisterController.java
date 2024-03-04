package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.Admin;
import org.example.domain.Theatre;
import org.example.domain.validators.ValidationException;
import org.example.service.Exceptionn;
import org.example.service.IService;

import java.io.IOException;

public class RegisterController{
    IService service;
    @FXML
    PasswordField passwordField;
    @FXML
    Label registerLabel;
    @FXML
    Label emailLabel;
    @FXML
    Label passwordLabel;
    @FXML
    Label validationCodeLabel;
    @FXML
    Button registerButton;
    @FXML
    TextField emailTextField;
    @FXML
    TextField validationCodeTextField;

    public void setService(IService service) {
        this.service = service;
    }

    @FXML
    protected void registerButtonClicked() {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String validationCode = validationCodeTextField.getText();

        boolean ok = true;

        try {
            Admin admin = new Admin(email, password, new Theatre("Teatrul National", "Bucuresti, str. Lalelelor, nr. 1", validationCode));
            service.addAdmin(admin);
        } catch (ValidationException | Exceptionn e) {
            ok = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Registration failed!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        if (ok) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Info");
            alert.setHeaderText("WELCOME, ADMIN!");
            alert.setContentText("You have been successfully registered as an admin.\n");
            alert.showAndWait();

            Stage toBeClosed = (Stage) registerButton.getScene().getWindow();
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
            emailTextField.clear();
            passwordField.clear();
            validationCodeTextField.clear();
        }
    }
}
