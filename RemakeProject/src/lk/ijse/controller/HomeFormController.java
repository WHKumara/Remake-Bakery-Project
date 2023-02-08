package lk.ijse.controller;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.PurchaseOrderBO;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;
import java.sql.SQLException;

public class HomeFormController {
    public AnchorPane pane;
    public Label lblCustomerCount;
    public Label lblTotalOrders;
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);
    public void initialize() throws SQLException, ClassNotFoundException {
        lblCustomerCount.setText(String.valueOf(customerBO.customerCount()));
        lblTotalOrders.setText(String.valueOf(purchaseOrderBO.getOrderCount()));
    }

    public void imgOrderAction(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.ORDER_DETAIL,pane);
    }

    public void imgCustomerAction(MouseEvent mouseEvent) {
    }
}
