package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.SuperBO;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCustomerFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblCustId;

    @FXML
    private TextField txtCustName;

    @FXML
    private TextField txtCustContact;

    @FXML
    private TextArea txtCustAddress2;

    @FXML
    private Label lblContactWarning;

    @FXML
    private Label lblNameWarning;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        loadNextCustomerId();
    }
    @FXML
    void backAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,pane);
    }

    @FXML
    void btnAddAction(ActionEvent event) {
        if(isMatchContact() & isMatchName()){
            if(!txtCustName.getText().equals("")){
                String custName = txtCustName.getText();
                int custContact = Integer.parseInt(txtCustContact.getText());
                String custAddress = txtCustAddress2.getText();

                //methanata to pakge eke object walta data danawa
                //Customer cust = new Customer(lblCustId.getText(),custName,custContact,custAddress);

                try {
                    //CustomerModal.addCustomer(cust);
                    customerBO.saveCustomer(new CustomerDTO(lblCustId.getText(),custName,custContact,custAddress));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"Customer is added!", ButtonType.OK);
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.get() == ButtonType.OK){
                        Navigation.navigate(Routes.ADD_CUSTOMER,pane);
                    }
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                new Alert(Alert.AlertType.WARNING,"Invalied Input");
            }
    }
    }

    @FXML
    void btnClearAction(ActionEvent event) throws IOException {
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure whether do you want to cancle this ?",
                    ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.YES){
                Alert alert1 = new Alert(Alert.AlertType.WARNING, "Customer is not Added !.Do you want to back ?",
                        ButtonType.OK);
                Optional<ButtonType> result1 = alert1.showAndWait();
                if (result1.get()==ButtonType.OK){
                   Navigation.navigate(Routes.CUSTOMER,pane);
                }
            }
    }
    private void loadNextCustomerId() {
        try {
           // String CustId = CustomerModal.generateNextEmployeeId();
            String CustId = customerBO.generateNewID();
            lblCustId.setText(CustId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isMatchName() {
        Pattern pattern = Pattern.compile("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+");
        Matcher matcher = pattern.matcher(txtCustName.getText());

        boolean isMatchName = matcher.matches();

        if (!isMatchName){
            txtCustName.clear();
            txtCustName.requestFocus();
            lblNameWarning.setText("Invalid Input.");
        }else{
            lblNameWarning.setText("");
        }
        return isMatchName;
    }

    public boolean isMatchContact() {
        Pattern pattern = Pattern.compile("^(07)([0-9]{8,8})$");
        Matcher matcher = pattern.matcher(txtCustContact.getText());

        boolean isMatchContact = matcher.matches();

        if (!isMatchContact){
            txtCustContact.clear();
            txtCustContact.requestFocus();
            lblContactWarning.setText("Invalied Input.");
        }else{
            lblContactWarning.setText("");
        }
        return isMatchContact;
    }
}
