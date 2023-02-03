package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.SuperBO;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddItemFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblItemCode;

    @FXML
    private TextField txtItemType;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private Label lblQtyWarning;
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    @FXML
    private Label lblPriceWarning;
    public void initialize(){
        loadNextEmployeeId();
    }

    @FXML
    void backAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.STORE,pane);
    }

    @FXML
    void btnAddAction(ActionEvent event) {
        boolean isMatchPrice = isMatchPrice();
        boolean isMatchQty = isMatchQty();
        if(isMatchQty && isMatchPrice){
            if(!txtItemType.getText().equals("")){
                String itemType = txtItemType.getText();
                int itemQty = Integer.parseInt(txtQuantity.getText());
                double unitPrice = Double.parseDouble(txtUnitPrice.getText());

                //methanata to pakge eke object walta data danawa
                //Item item = new Item(lblItemCode.getText(),itemQty,itemType,unitPrice);
                ItemDTO item = new ItemDTO(lblItemCode.getText(),itemQty,itemType,unitPrice);
                try {
                   // ItemModal.addItem(item);
                    itemBO.saveItem(item);

                    Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure whether do you want to ADD this ?",
                            ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get()==ButtonType.YES){
                        Alert alert1 = new Alert(Alert.AlertType.WARNING, "Item is Added !.",
                                ButtonType.OK);
                        Optional<ButtonType> result1 = alert1.showAndWait();
                        if (result1.get()==ButtonType.OK){
                            loadNextEmployeeId();
                            clearTextFeild();
                        }
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }else{
                Alert alert= new Alert(Alert.AlertType.WARNING,"Invalid Input!!",ButtonType.OK);
                alert.show();
            }
        }
    }

    @FXML
    void btnCancelAction(ActionEvent event) throws IOException {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether do you want to cancle this ?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.YES){
            Alert alert1 = new Alert(Alert.AlertType.WARNING, "Item is not Added !.",
                    ButtonType.OK);
            Optional<ButtonType> result1 = alert1.showAndWait();
            if (result1.get()==ButtonType.OK)
                Navigation.navigate(Routes.STORE,pane);
        }
    }
    private void loadNextEmployeeId() {
        try {
            //String itemId = ItemModal.generateNextItemId();
            String itemId = itemBO.generateItemID();
            lblItemCode.setText(itemId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearTextFeild(){
        txtItemType.clear();
        txtQuantity.clear();
        txtUnitPrice.clear();
    }

    public boolean isMatchQty(){
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(txtQuantity.getText());

        boolean isMatchQty = matcher.matches();

        if (!isMatchQty) {
            lblQtyWarning.setText("Invalid Input.");
            txtQuantity.clear();
            txtQuantity.requestFocus();
        } else {
            lblQtyWarning.setText("");
        }
        return isMatchQty;
    }
    public boolean isMatchPrice(){
        Pattern pattern = Pattern.compile("^[0-9]+\\.[0-9]{2}$");
        Matcher matcher = pattern.matcher(txtUnitPrice.getText());

        boolean isMatchPrice = matcher.matches();

        if (!isMatchPrice) {
            lblPriceWarning.setText("Invalid Input.");
            txtUnitPrice.clear();
            txtUnitPrice.requestFocus();
        } else {
            lblPriceWarning.setText("");
        }
        return isMatchPrice;
    }
}
