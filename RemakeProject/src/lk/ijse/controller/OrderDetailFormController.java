package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PurchaseOrderBO;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;
import lk.ijse.view.tm.OrderDetailTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<OrderDetailTm> tblOrderDetail;

    @FXML
    private TableColumn<?, ?> coleOrderId;

    @FXML
    private TableColumn<?, ?> colCostId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private Label lblIncome;

    @FXML
    private TextField txtSearch;
    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        setData();

        double amount = purchaseOrderBO.getTotalAmount();
        lblIncome.setText(String.valueOf(amount ));

    }
    private void setData() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailTm> list = purchaseOrderBO.getData();
        ObservableList<OrderDetailTm> data = FXCollections.observableArrayList();

        data.addAll(list);

        tblOrderDetail.setItems(data);

        System.out.println(data);
    }
    public void setCellValueFactory(){
        coleOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCostId.setCellValueFactory(new PropertyValueFactory<>("CustId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
    @FXML
    void imgBackAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.HOME,pane);
    }

    @FXML
    void imgSearchAction(MouseEvent event) {
        txtSearch.requestFocus();
    }

    @FXML
    void txtSearchAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        search();
    }
    public void search() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailTm> list = purchaseOrderBO.getData();
        ObservableList<OrderDetailTm> data = FXCollections.observableArrayList();

        data.addAll(list);

        FilteredList<OrderDetailTm> filteredList = new FilteredList(data, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(orderDetailTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (orderDetailTm.getOrderId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (orderDetailTm.getCustId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(orderDetailTm.getDate()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<OrderDetailTm> sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tblOrderDetail.comparatorProperty());
        tblOrderDetail.setItems(sortedList);
    }
}
