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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.gymmanagmentsystem.db.DBConnection;
import lk.ijse.gymmanagmentsystem.dto.tm.iniLinechart;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.spi.ResourceBundleControlProvider;
import java.util.Date;
import java.text.SimpleDateFormat;


public class Dashboard implements Initializable {
    public AnchorPane root;
    public JFXButton logoutbtn;
    public JFXButton txtInsUpdateAndDelete;
    public JFXButton memberAddbtn;
    public JFXButton btnInsAdd;
    public JFXButton updateAndDeletebtn;
    public JFXButton reportbtn;
    public JFXButton txtReportIns;
    public JFXButton paymentbtn;
    public CategoryAxis xValue;
    public NumberAxis yValue;
    public JFXButton dashboardbtn;
    public Text dateTime;
    public Text date;
    public Label label2;
    public Label label1;
    public Label monthlyIncome;
    public Button srchbtn;
    public Label srchlabel;
    public TextField txtmemberID;
    public TextField txtMonth;
    public Button Clearbtn;
    public Button cusReport;
    public Button insReport;
    public Button supReport;
    public Button payReport;

    private boolean bool = true;
    public String time;

    @FXML
    private LineChart iniLinechart;


    public void initialize(URL url, ResourceBundle rb) {

        new Thread() {
            public void run() {
                while(bool) {
                    try {
                        Thread.sleep(1000);
                    }catch(Exception e){}
                    Date currentDate = new Date();
                    SimpleDateFormat clockFormat = new SimpleDateFormat("h:mm:ss a");
                    //System.out.println(clockFormat.format(currentDate));
                    time = (clockFormat.format(currentDate));
                    dateTime.setText(time);
                }
            }
        }.start();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // get the current date and time
        LocalDateTime datetime = LocalDateTime.now();

        // update the label with the formatted date and time string
        date.setText(formatter.format(datetime));


        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Monthly income");

        try {
            // connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gymmanagement", "root", "1234");

            // retrieve the payment data for each month
            PreparedStatement stmt = conn.prepareStatement("SELECT SUM(amount) as total FROM Payment WHERE payment_date BETWEEN ? AND ?");
            for (int month = 1; month <= 12; month++) {
                LocalDate start = LocalDate.of(2023, month, 1);
                LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
                stmt.setDate(1, java.sql.Date.valueOf(start));
                stmt.setDate(2, java.sql.Date.valueOf(end));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Double totalAmount = rs.getDouble("total");
                    series.getData().add(new XYChart.Data<>(Month.of(month).toString(), totalAmount));
                } else {
                    series.getData().add(new XYChart.Data<>(Month.of(month).toString(), 0.0));
                }
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        iniLinechart.getData().add(series);

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gymmanagement", "root", "1234");
            // Retrieve the total number of members from the members table
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM member");
            if (rs.next()) {
                int count = rs.getInt(1);
                label1.setText("" + count);
            }
            rs.close();
            stmt.close();

            // Retrieve the total number of instructors from the instructor table
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM instructor");
            if (rs.next()) {
                int count = rs.getInt(1)-1;
                label2.setText("" + count);
            }
            rs.close();
            stmt.close();


        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        YearMonth currentYearMonth = YearMonth.now();
        int currentMonth = currentYearMonth.getMonthValue();
        int currentYear = currentYearMonth.getYear();

        try{

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gymmanagement", "root", "1234");

            // SQL query to get the total income of the current month
            String query = "SELECT SUM(amount) AS total_income "
                    + "FROM Payment "
                    + "WHERE YEAR(payment_date) = ? AND MONTH(payment_date) = ?";

            PreparedStatement stmt = conn.prepareStatement(query);

            // set parameters for the current year and month
            stmt.setInt(1, currentYear);
            stmt.setInt(2, currentMonth);

            ResultSet rs = stmt.executeQuery();

            // get the total income of the current month
            if (rs.next()) {
                double totalIncome = rs.getDouble("total_income");
                monthlyIncome.setText("" + totalIncome);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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


    public void srchbtnOnAction(ActionEvent event) {
        String memberId = txtmemberID.getText();
        String month = txtMonth.getText();

        // Create a connection to the database
        try  {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gymmanagement", "root", "1234");

            // Prepare the SQL statement to retrieve the payments
            String sql = "SELECT * FROM Payment WHERE member_id=? AND MONTH(payment_date)=? AND YEAR(payment_date)=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.setInt(2, Integer.parseInt(month.substring(0, 2)));
            pstmt.setInt(3, Integer.parseInt(month.substring(3, 7)));

            // Execute the query and get the result set
            ResultSet rs = pstmt.executeQuery();

            // Check if any payments were found
            if (rs.next()) {
                // Payment found, set the label text to indicate payment was made
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
                String paymentDate = dateFormat.format(rs.getDate("payment_date"));
                srchlabel.setText("Payment made for " + paymentDate);
            } else {
                // No payments found, set the label text to indicate payment was not made
                srchlabel.setText("Payment not made for " + month);
            }
        } catch (SQLException ex) {
            // Handle any SQL errors
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            // Handle any invalid input errors
            srchlabel.setText("Invalid input for month");
        }
    }


    public void ClearbtnOnAction(ActionEvent event) {
        txtmemberID.setText("");
        txtMonth.setText("");
        srchlabel.setText("");

    }

    public void cusReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/reports/member.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/reports/Instructer.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void supReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/reports/suppliment.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void payReportOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/reports/payment.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

