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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gymmanagmentsystem.dto.Customer;
import lk.ijse.gymmanagmentsystem.dto.Payment;
import lk.ijse.gymmanagmentsystem.dto.tm.CustomerTM;
import lk.ijse.gymmanagmentsystem.dto.tm.PaymentTM;
import lk.ijse.gymmanagmentsystem.model.PaymentModel;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/gymmanagement";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public AnchorPane root;
    public JFXButton memberAddbtn;
    public JFXButton updateAndDeletebtn;
    public JFXButton reportbtn;
    public ImageView paymentbtn;
    public JFXButton logoutbtn;
    public TextField memberidtxt;
    public Button txtSearch;
    public TableColumn coPaymentID;
    public TableColumn coPaymentDate;
    public TableColumn coPaymentAmount;
    public TableColumn coMemberId;

    public TableView<PaymentTM> tblPayment;
    public TextField btnpname;
    public TextField btnpamount;
    public Button btnpayed;
    public TextField btnpid;
    public Button psearch;
    public DatePicker btnpdat;
    public ImageView txtrefresh;
    public JFXButton btnrefresh;
    public JFXButton btnpayment;
    public JFXButton updateAndDeletebtn1;
    public JFXButton btnInsAdd;
    public JFXButton txtReportIns;
    public JFXButton txtInsUpdateAndDelete;
    public JFXButton dashboardbtn;
    public TextField paymentDelete;
    public Button DeleteButtonPayment;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void setCellValueFactory() {
        coPaymentID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        coPaymentDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        coPaymentAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        coMemberId.setCellValueFactory(new PropertyValueFactory<>("MemberId"));


    }

    private void getAll() {
        try {
            ObservableList<PaymentTM> pList = FXCollections.observableArrayList();
            List<Payment> payList = PaymentModel.getAll();

            for (Payment payment : payList) {
                pList.add(new PaymentTM(
                        payment.getId(),
                        payment.getDate(),
                        payment.getAmount(),
                        payment.getMemberId()
                ));
            }
            tblPayment.setItems(pList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, " Error!").show();
        }
    }

    @FXML
    void btnpayedOnAction(ActionEvent event) throws SQLException {
        String id = btnpid.getText();
        LocalDate date = btnpdat.getValue();
        Double amount= Double.valueOf(btnpamount.getText());
        String member_id = memberidtxt.getText();

        YearMonth yearMonth = YearMonth.from(date);

        try (Connection con = DriverManager.getConnection(URL, props)) {
            // Query the database to check for existing payments
            String sql = "SELECT COUNT(*) FROM payment WHERE member_id = ? AND YEAR(payment_date) = ? AND MONTH(payment_date) = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member_id);
            pstmt.setInt(2, yearMonth.getYear());
            pstmt.setInt(3, yearMonth.getMonthValue());
            ResultSet rs = pstmt.executeQuery();

            // If there is already a payment for the same month and year, show an error message and return
            if (rs.next() && rs.getInt(1) > 0) {
                new Alert(Alert.AlertType.ERROR, "A payment for the same month and year already exists.").show();
                return;
            }

            // If there is no existing payment, proceed with the payment
            sql = "INSERT INTO payment(payment_id, payment_date, amount, member_id) VALUES (?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, String.valueOf(date));
            pstm.setDouble(3, amount);
            pstm.setString(4, member_id);

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payed :)").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error! Try again ").show();
        }
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


    public void btnpOnSearch(ActionEvent event) {

        String Paymentid = memberidtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT * FROM member WHERE member_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,Paymentid);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString(2);


                btnpname.setText(name);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void btnrefreshOnAcrtion(ActionEvent event)throws IOException{
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/view/payment.fxml"));
        Stage window = (Stage)btnrefresh.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader));
        window.setTitle("Payment");
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

    public void DeleteButtonPaymentOnAction(ActionEvent event) {
        String id = paymentDelete.getText();
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "DELETE FROM payment WHERE payment_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment details deletd!").show();
                memberidtxt.setText("");
                btnpid.setText("");
                btnpamount.setText("");
                btnpdat.setValue(LocalDate.parse(""));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
