package lk.ijse.gymmanagmentsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gymmanagmentsystem.dto.tm.CustomerTM;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;

public class InstructorAdd  {
    private static final String URL = "jdbc:mysql://localhost:3306/gymmanagement";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }


    public AnchorPane root;
    public JFXButton logoutbtn;
    public JFXButton memberAddbtn;
    public JFXButton updateAndDeletebtn;
    public JFXButton reportbtn;
    public JFXButton paymentbtn;
    public TextField txtNameIns;
    public TextField txtAdressIns;
    public TextField txtEmailIns;
    public TextField txtMobileNumberIns;
    public TextField Insidtxt;
    public JFXButton btnInsAdd;
    public JFXButton txtReportIns;
    public JFXButton txtInsUpdateAndDelete;
    public JFXButton dashboardbtn;


    public void btnSaveOnAction(ActionEvent event)throws IOException {
        String id = Insidtxt.getText();
        String name = txtNameIns.getText();
        String address = txtAdressIns.getText();
        String email = txtEmailIns.getText();
        String contact=txtMobileNumberIns.getText();


        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO instructor (instructor_id, name, address, email, contact )" +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.setString(4, email);
            pstm.setString(5, contact);

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


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "huree!! instructor added successful :)")
                        .show();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Sorry!! instructor added ussuccessful ").show();
        }

    }

    public void btnResetOnAction(ActionEvent event)throws IOException {
        Insidtxt.setText("");
        txtNameIns.setText("");
        txtAdressIns.setText("");
        txtEmailIns.setText("");
        txtMobileNumberIns.setText("");


    }


    @FXML
    void btnUpdateAndDeleteOnAction(ActionEvent event) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/custmerUpdateAndDelete.fxml"));
        Stage window = (Stage)updateAndDeletebtn.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Customer Update And Delete ");
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
        window.setTitle("Member Add");
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
