package lk.ijse.gymmanagmentsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.gymmanagmentsystem.dto.Customer;
import lk.ijse.gymmanagmentsystem.dto.tm.CustomerTM;
import lk.ijse.gymmanagmentsystem.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class reportController implements Initializable {
    public JFXButton updateAndDeletebtn;
    public JFXButton memberAddbtn;
    public JFXButton updateAndDeletebtn1;
    public JFXButton paymentbtn;
    public JFXButton logoutbtn;
    public JFXButton reportbtn;
    public TableColumn coMemberId;
    public TableColumn coName;
    public TableColumn coContactNumber;
    public TableColumn coAddress;
    public TableColumn coAge;
    public TableColumn coEmail;
    public TableColumn coJoinDate;
    public TableColumn coGender;
    public TableColumn coMembershipType;

    public TableView<CustomerTM> cltblcustomer;
    public JFXButton btnInsAdd;
    public JFXButton txtReportIns;
    public JFXButton txtInsUpdateAndDelete;
    public JFXButton dashboardbtn;


    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void setCellValueFactory() {
        coMemberId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        coName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        coAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        coAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        coGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        coEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        coContactNumber.setCellValueFactory(new PropertyValueFactory<>("MobileNumber"));
        coJoinDate.setCellValueFactory(new PropertyValueFactory<>("JoinDate"));
        coMembershipType.setCellValueFactory(new PropertyValueFactory<>("MembershipType") );




    }

    private void getAll() {
        try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<Customer> cusList = CustomerModel.getAll();

            for (Customer customer : cusList) {
                obList.add(new CustomerTM(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getAge(),
                        customer.getGender(),
                        customer.getEmail(),
                        customer.getMobileNumber(),
                        customer.getJoinDate(),
                        customer.getMembershipType()
                ));
            }
            cltblcustomer.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }



    @FXML
    void btnUpdateAndDeleteOnAction(ActionEvent event) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/custmerUpdateAndDelete.fxml"));
        Stage window = (Stage)updateAndDeletebtn.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Customer Update And ");
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
        window.setTitle("Home Page");
        window.setMaximized(true);
        window.centerOnScreen();
    }
    public void btnInsAddOnAction(ActionEvent event) throws IOException{
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/instructorAdd.fxml"));
        Stage window = (Stage)btnInsAdd.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Home Page");
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
        window.setTitle("Instructor Report");
        window.setMaximized(true);
        window.centerOnScreen();
    }

}
