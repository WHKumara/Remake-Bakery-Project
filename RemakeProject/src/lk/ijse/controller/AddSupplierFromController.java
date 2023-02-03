package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.SuperBO;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddSupplierFromController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblSuppId;

    @FXML
    private TextField txtSupName;

    @FXML
    private TextField txtSuppAddress;

    @FXML
    private TextField txtSuppContact;

    @FXML
    private TextField txtSuppEmail;

    @FXML
    private ImageView S;

    @FXML
    private Label lblMailWarning;

    @FXML
    private Label lblContactWarning;

    @FXML
    private Label lblNameWarning;
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize(){
        loadNextSupplierId();

    }
    @FXML
    void backAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,pane);
    }

    @FXML
    void btnAddAction(ActionEvent event) {
        boolean isMatchContact = txtContactAction(event);
        boolean isMatchMail = txtMailOnAction(event);
        boolean isMatchName = txtNameAction(event);

        if(isMatchContact && isMatchMail & isMatchName){
            String supName = txtSupName.getText();
            int contact = Integer.parseInt(txtSuppContact.getText());
            String supAddress = txtSuppAddress.getText();
            String supEmail = txtSuppEmail.getText();

            //methanata to pakge eke object walta data danawa
            //Supplier supp = new Supplier(lblSuppId.getText(),supName,contact,supAddress,supEmail);

            try {
                //SupplierModal.addSupplier(supp);
                supplierBO.saveSupplier(new SupplierDTO(lblSuppId.getText(),supName,contact,supAddress,supEmail));

                Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure whether do you want to ADD this ?",
                        ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get()==ButtonType.YES){
                    Alert alert1 = new Alert(Alert.AlertType.WARNING, "Supplier is Added !.",
                            ButtonType.OK);
                    Optional<ButtonType> result1 = alert1.showAndWait();
                    if (result1.get()==ButtonType.OK){
                        clearText();
                        loadNextSupplierId();
                    }

                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    @FXML
    void btnClearAction(ActionEvent event) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether do you want to cancle this ?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.YES){
            Alert alert1 = new Alert(Alert.AlertType.WARNING, "Supplier is not Added !.",
                    ButtonType.OK);
            Optional<ButtonType> result1 = alert1.showAndWait();
            if (result1.get()==ButtonType.OK) {
                clearText();
            }
        }
    }

    @FXML
    boolean txtContactAction(ActionEvent event) {
        Pattern pattern = Pattern.compile("^(07)([0-9]{8,8})$");
        Matcher matcher = pattern.matcher(txtSuppContact.getText());

        boolean isMatch = matcher.matches();

        if (!isMatch){
            txtSuppContact.clear();
            txtSuppContact.requestFocus();
            lblContactWarning.setText("Invalid Input !");
        }else{
            lblContactWarning.setText("");
        }
        return isMatch;
    }

    @FXML
    boolean txtNameAction(ActionEvent event) {
        Pattern pattern = Pattern.compile("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+");
        Matcher matcher = pattern.matcher(txtSupName.getText());

        boolean isMatch = matcher.matches();

        if (!isMatch){
            txtSupName.clear();
            txtSupName.requestFocus();
            lblNameWarning.setText("Invalid Input !");
        }else{
            lblNameWarning.setText("");
        }
        return isMatch;
    }

    @FXML
    boolean txtMailOnAction(ActionEvent event) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(txtSuppEmail.getText());

        boolean isMatch = matcher.matches();

        if (!isMatch){
            txtSuppEmail.clear();
            txtSuppEmail.requestFocus();
            lblMailWarning.setText("Invalid Input !");
        }else{
            lblMailWarning.setText("");
        }
        return  isMatch;
    }
    private void loadNextSupplierId() {
        try {
            //String supId = SupplierModal.generateNextSupplierId();
            String supId = supplierBO.generateSupplierID();
            lblSuppId.setText(supId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearText(){
        txtSupName.clear();
        txtSuppAddress.clear();
        txtSuppContact.clear();
        txtSuppEmail.clear();
    }
}
