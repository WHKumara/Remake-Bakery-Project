package lk.ijse.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;

    public static void navigate(Routes route,AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route){
            case CUSTOMER:
                window.setTitle("Customer Manage Form");
                selectUI("CustomerForm.fxml");
                break;
            case LOGIN:
                window.setTitle("Logging Form");
                selectUI("LoginForm.fxml");
                break;
            case EMPLOYEE:
                window.setTitle("Employee Form");
                selectUI("EmployeeForm.fxml");
                break;
            case ADMIN:
                window.setTitle("Admin Form");
                selectUI("AdminForm.fxml");
                break;
            case HOME:
                window.setTitle("Home Form");
                selectUI("HomeForm.fxml");
                break;
            case ADD_EMPLOYEE:
                window.setTitle("Add Employee Form");
                selectUI("AddEmployeeForm.fxml");
                break;
            case STORE:
                window.setTitle("Store Manage Form");
                selectUI("StoreManageForm.fxml");
                break;
            case ADD_ITEM:
                window.setTitle("Add Item Form");
                selectUI("AddItemForm.fxml");
                break;
            case RAW_MATERIAL:
                window.setTitle("Raw Material Form");
                selectUI("RawMaterialForm.fxml");
                break;
            case PAYMENT:
                window.setTitle("Payment Form");
                selectUI("PaymentsForm.fxml");
                break;
            case SUPPLIER:
                window.setTitle("Supplier Form");
                selectUI("SupplierForm.fxml");
                break;
            case ADD_SUPPLIER:
                window.setTitle("Add Supplier Form");
                selectUI("AddSupplierForm.fxml");
                break;
            case ADD_CUSTOMER:
                window.setTitle("Add Customer Form");
                selectUI("AddCustomerForm.fxml");
                break;
            case ADD_MATERIAL:
                window.setTitle("Add Material Form");
                selectUI("AddMaterialForm.fxml");
                break;
            case ADD_PAYMENT:
                window.setTitle("Add Payment Form");
                selectUI("AddPaymentForm.fxml");
                break;
            case ORDER:
                window.setTitle("Order Form");
                selectUI("OrderForm.fxml");
                break;
            case ORDER_DETAIL:
                window.setTitle("Order Detail Form");
                selectUI("OrderDetailForm.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }
    }

    private static void selectUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class
                .getResource("/lk/ijse/view/" + location)));
    }
}
