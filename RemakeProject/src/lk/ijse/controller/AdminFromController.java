package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.PurchaseOrderBO;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class AdminFromController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblTopic;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblCurrentTime;

    @FXML
    private AnchorPane homePane;

    @FXML
    private Label lblTotalOrders;

    @FXML
    private Label lblCustomerCount;
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);

    public void initialize() throws SQLException, ClassNotFoundException {
        showDate();
        showTime();
        lblCustomerCount.setText(String.valueOf(customerBO.customerCount()));
        lblTotalOrders.setText(String.valueOf(purchaseOrderBO.getOrderCount()));
    }

    @FXML
    void btnLogOutAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to logout ?", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get()==ButtonType.YES){
            Navigation.navigate(Routes.LOGIN,pane);
        }
    }

    @FXML
    void clickCustomers(MouseEvent event) throws IOException {
        lblTopic.setText("Customer");
        Navigation.navigate(Routes.CUSTOMER,homePane);
    }

    @FXML
    void clickEmployee(MouseEvent event) throws IOException {
        lblTopic.setText("Employee");
        Navigation.navigate(Routes.EMPLOYEE,homePane);
    }

    @FXML
    void clickHome(MouseEvent event) throws IOException {
        lblTopic.setText("Home");
        Navigation.navigate(Routes.HOME,homePane);
    }

    @FXML
    void clickOrder(MouseEvent event) throws IOException {
        lblTopic.setText("Order");
        Navigation.navigate(Routes.ORDER,homePane);
    }

    @FXML
    void clickPayments(MouseEvent event) throws IOException {
        lblTopic.setText("Payments");
        Navigation.navigate(Routes.PAYMENT,homePane);
    }

    @FXML
    void clickRawMaterial(MouseEvent event) throws IOException {
        lblTopic.setText("Raw Materials");
        Navigation.navigate(Routes.RAW_MATERIAL,homePane);
    }

    @FXML
    void clickStore(MouseEvent event) throws IOException {
        lblTopic.setText("Store");
        Navigation.navigate(Routes.STORE,homePane);
    }

    @FXML
    void clickSuppliers(MouseEvent event) throws IOException {
        lblTopic.setText("Supplier");
        Navigation.navigate(Routes.SUPPLIER,homePane);
    }

    @FXML
    void imgCustomerAction(MouseEvent event) {

    }

    @FXML
    void imgOrderAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.ORDER_DETAIL,homePane);
    }
    public void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String date = s.format(d);
        lblDate.setText(date);

    }

    private void showTime() {

        Timeline timeline=new Timeline(
                new KeyFrame(Duration.ZERO, e->{
                    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm:ss");
                    lblCurrentTime.setText(LocalDateTime.now().format(formatter));
                }),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

}

