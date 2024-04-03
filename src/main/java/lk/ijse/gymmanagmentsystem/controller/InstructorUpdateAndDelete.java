package lk.ijse.gymmanagmentsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class InstructorUpdateAndDelete {

    private static final String URL = "jdbc:mysql://localhost:3306/gymmanagement";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXButton updateAndDeletebtn;
    public TextField txtNameIns;
    public TextField txtAdressIns;
    public TextField txtEmailIns;
    public TextField txtMobileNumberIns;
    public Button btnupdateIns;
    public Button bundleIns;
    public TextField Insidtxt;
    public Button txtSearchIns;
    public JFXButton logoutbtn;
    public JFXButton memberAddbtn;
    public JFXButton btnInsAdd;
    public JFXButton updateAndDeletebtn1;
    public JFXButton reportbtn;
    public JFXButton paymentbtn;
    public JFXButton txtReportIns;
    public JFXButton txtInsUpdateAndDelete;
    public JFXButton dashboardbtn;

    public void btnUpdateOnACtion(ActionEvent event) throws SQLException {
        String id = Insidtxt.getText();
        String name = txtNameIns.getText();
        String address = txtAdressIns.getText();
        String email = txtEmailIns.getText();
        String contact=txtMobileNumberIns.getText();


        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE instructor SET name = ?, address = ?, email= ?, contact = ? WHERE instructor_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setString(3, email);
            pstm.setString(4, contact);
            pstm.setString(5, id);

            // check if email has @gmail.com
            if (!email.endsWith("@gmail.com")) {
                new Alert(Alert.AlertType.ERROR, "Email must end with @gmail.com").show();
                return;
            }

// check if contact has up to 10 numbers
            if (contact.replaceAll("[^\\d]", "").length() > 10 || contact.replaceAll("[^\\d]", "").length() < 10 ) {
                new Alert(Alert.AlertType.ERROR, "Contact cannot have more than 10 digits").show();
                return;
            }


            boolean isUpdated = pstm.executeUpdate() > 0;
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "yes! Instructor updated successful!!").show();
            }

        }

    }

    public void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String id = Insidtxt.getText();
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM instructor WHERE instructor_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Instructor Deletd!").show();
                Insidtxt.setText("");
                txtNameIns.setText("");
                txtAdressIns.setText("");
                txtEmailIns.setText("");
                txtMobileNumberIns.setText("");

            }
        }

    }

    @FXML
    public void codeonOnSearch(ActionEvent event)throws SQLException {

        String id = Insidtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT * FROM instructor WHERE instructor_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,id);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String email = resultSet.getString(4);
                String contact = resultSet.getString(5);


                txtNameIns.setText(name);
                txtAdressIns.setText(address);
                txtEmailIns.setText(email);
                txtMobileNumberIns.setText(contact);
            }
        }
    }


    @FXML
    void btnUpdateAndDeleteOnAction(ActionEvent event) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/custmerUpdateAndDelete.fxml"));
        Stage window = (Stage)updateAndDeletebtn.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Customer Update And Delete");
        window.setMaximized(true);
        window.centerOnScreen();

    }


    public void btnReportOnAction(ActionEvent event) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/report.fxml"));
        Stage window = (Stage)reportbtn.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Report");
        window.setMaximized(true);
        window.centerOnScreen();


    }

    public void btnPaymentOnAction(ActionEvent event) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/payment.fxml"));
        Stage window = (Stage)paymentbtn.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Payment");
        window.setMaximized(true);
        window.centerOnScreen();

    }

    public void btnMemberAddOnAction(ActionEvent event) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));
        Stage window = (Stage)memberAddbtn.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Home Page");
        window.setMaximized(true);
        window.centerOnScreen();
    }

    public void btnLogoutOnACtion(ActionEvent event) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/logout.fxml"));
        Stage window = (Stage)logoutbtn.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Logout");
        window.setMaximized(true);
        window.centerOnScreen();
    }

    public void btnInsAddOnAction(ActionEvent event) throws IOException{
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/instructorAdd.fxml"));
        Stage window = (Stage)btnInsAdd.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Instructor Add");
        window.setMaximized(true);
        window.centerOnScreen();

    }

    public void txtInsUpdateAndDeleteOnAction(ActionEvent event) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/instructorUpdateAndDelete.fxml"));
        Stage window = (Stage)txtInsUpdateAndDelete.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("instructor Update And Delete");
        window.setMaximized(true);
        window.centerOnScreen();
    }
    public void txtReportInsOnAction(ActionEvent event) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/reportInstructor.fxml"));
        Stage window = (Stage)txtReportIns.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Instructor Report");
        window.setMaximized(true);
        window.centerOnScreen();
    }
    public void dashboardbtnOnAction(ActionEvent event) throws IOException{
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
        Stage window = (Stage)dashboardbtn.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Dashboard");
        window.setMaximized(true);
        window.centerOnScreen();
    }
}
