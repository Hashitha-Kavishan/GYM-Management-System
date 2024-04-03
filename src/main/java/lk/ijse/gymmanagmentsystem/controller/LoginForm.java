package lk.ijse.gymmanagmentsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginForm implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";


    @FXML
    private AnchorPane root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    void loginbutton(ActionEvent e) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {


            // Get the current stage
            Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            // Load the LoginPageForm and create a new stage
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Dash Board");
            stage.setMaximized(true);
            stage.centerOnScreen();
            stage.show();


            // Close the current stage
            currentStage.close();
        } else {
            errorLabel.setText("Invalid username or password");
        }
    }


}
