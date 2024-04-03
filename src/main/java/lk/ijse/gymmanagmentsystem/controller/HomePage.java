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

public class HomePage implements Initializable {
    private static final String URL = "jdbc:mysql://localhost:3306/gymmanagement";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public TableView<CustomerTM> tblCustomer;
    public TextField memberidtxt;
    public ImageView InsAddOnAction;
    public JFXButton btnInsAdd;
    public JFXButton txtInsUpdateAndDelete;
    public JFXButton txtReportIns;
    public JFXButton dashboardbtn;


    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddrss;

    @FXML
    private TableColumn<?, ?> colMobileNumber;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colMembershipType;


    @FXML
    private AnchorPane root;


    @FXML
    private TextField txtName;

    @FXML
    private TextField txtMobileNumber;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtMembershipType;

    @FXML
    private JFXButton updateAndDeletebtn;

    @FXML
    private JFXButton reportbtn;

    @FXML
    private JFXButton paymentbtn;

    @FXML
    private JFXButton memberAddbtn;

    @FXML
    private JFXButton logoutbtn;



    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        getAll();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddrss.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobileNumber.setCellValueFactory(new PropertyValueFactory<>("MobileNumber"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colMembershipType.setCellValueFactory(new PropertyValueFactory<>("MembershipType"));

    }


    private void getAll() {
      /* try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<Customer> cusList = CustomerModel.getAll();

            for (Customer customer : cusList) {
                obList.add(new CustomerTM(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getAge(),
                        customer.getMobileNumber(),
                        customer.getEmail(),
                        customer.getGender(),
                        customer.getMembershipType()
                ));
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }*/
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String id = memberidtxt.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String age = txtAge.getText();
        String email = txtEmail.getText();
        String gender = txtGender.getText();
        String contact=txtMobileNumber.getText();
        LocalDate join_date=txtDate.getValue();
        String membershipType = txtMembershipType.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO member(member_id, name, address,age,gender,email,contact,join_date,membership_type_id)" +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.setString(4, age);
            pstm.setString(5, gender);
            pstm.setString(6, email);
            pstm.setString(7, contact);
            pstm.setString(8, String.valueOf(join_date));
            pstm.setString(9, membershipType);

            // check if any of the fields is empty
            if (name.isEmpty() || address.isEmpty() || age.isEmpty() || email.isEmpty() || gender.isEmpty() || contact.isEmpty() || join_date == null || membershipType.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
                return;
            }

            try {
                int ageInt = Integer.parseInt(age);
                if (ageInt > 150) {
                    new Alert(Alert.AlertType.ERROR, "Age cannot be greater than 150").show();
                    return;
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Age must be a number").show();
                return;
            }

// check if email has @gmail.com
            if (!email.endsWith("@gmail.com")) {
                new Alert(Alert.AlertType.ERROR, "Email must end with @gmail.com").show();
                return;
            }

// check if contact has up to 10 numbers
            if (contact.replaceAll("[^\\d]", "").length() > 10 || contact.replaceAll("[^\\d]", "").length() < 10) {
                new Alert(Alert.AlertType.ERROR, "Contact cannot have more than 10 digits").show();
                return;
            }

            // validate gender
            if (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female")) {
                new Alert(Alert.AlertType.ERROR, "Gender should be either male or female").show();
                return;
            }



            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "huree!! customer added successful :)")
                        .show();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "sorry!! customer added unsuccessful").show();
        }


    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException{
        memberidtxt.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtAge.setText("");
        txtEmail.setText("");
        txtGender.setText("");
        txtMobileNumber.setText("");
        txtDate.setValue(LocalDate.parse(""));
        txtMembershipType.setText("");

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