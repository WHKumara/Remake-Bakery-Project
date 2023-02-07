package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<SupplierDTO> tblSupplier;

    @FXML
    private TableColumn<?, ?> colSuppId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private Label lblSupId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblContact;

    @FXML
    private ImageView imgUpdate;

    @FXML
    private ProgressIndicator prgCircle;

    @FXML
    private Pane paneDelete;

    @FXML
    private ImageView imgUpdate1;

    @FXML
    private Pane paneUpdate;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextArea txtAddress;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ImageView imgUpdate11;

    @FXML
    private Label lblContactWarning;

    @FXML
    private Label lblAddressWarning;

    @FXML
    private TextField txtSearch;
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);


    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        setSupplierTable();
        selectedItem();
    }
    @FXML
    void addSupplierAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.ADD_SUPPLIER,pane);
    }
    public void setCellValueFactory(){
        colSuppId.setCellValueFactory(new PropertyValueFactory<>("SupID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("supContact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supAddress"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("supEmail"));
    }

    public void setSupplierTable() throws SQLException, ClassNotFoundException {
        ObservableList<SupplierDTO> data = FXCollections.observableArrayList();
        ArrayList<SupplierDTO> suppList = supplierBO.getSupplierDetail();
        data.addAll(suppList);
        tblSupplier.setItems(data);

    }
    public void selectedItem(){
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (null!= newValue){
                        setData((SupplierDTO) newValue);
                       // getAddress((SupplierDTO) newValue);
                    }
                }
        );
    }
    private void setData(SupplierDTO newValue) {
        lblSupId.setText(newValue.getSupID());
        lblName.setText(newValue.getSupName());
        lblContact.setText(String.valueOf(newValue.getSupContact()));
        txtAddress.setText(newValue.getSupAddress());
        txtName.setText(newValue.getSupName());
        txtContact.setText(String.valueOf("0"+newValue.getSupContact()));
    }
    @FXML
    void btnUpdateAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(isMatchContact()){

        }
        String name = txtName.getText();
        int contact = Integer.parseInt(txtContact.getText());
        String address = txtAddress.getText();

        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether do you want to UPDATE ?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.YES){
            //Supplier supplier = new Supplier(lblSupId.getText(),name,contact,address,"");
            SupplierDTO supplier = new SupplierDTO(lblSupId.getText(),name,contact,address,"");
            try {
                //SupplierModal.updateSupplier(supplier);
                supplierBO.updateSupplierData(supplier);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }finally {
                paneUpdate.setVisible(false);
                prgCircle.setVisible(false);
                imgUpdate.setVisible(true);
                paneDelete.setVisible(true);
                txtName.clear();
                txtContact.clear();
                txtAddress.clear();
                setSupplierTable();
            }
        }
    }

    @FXML
    void imgSearchAction(MouseEvent event) {
        txtSearch.requestFocus();
    }

    @FXML
    void onAddressTyped(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void onCancelAction(MouseEvent event) {
        paneUpdate.setVisible(false);
        paneDelete.setVisible(true);
        prgCircle.setVisible(false);
        imgUpdate.setVisible(true);
    }

    @FXML
    void onClickUpdate(MouseEvent event) {
        if(lblSupId.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }else {
            imgUpdate.setVisible(false);
            prgCircle.setVisible(true);
            paneUpdate.setVisible(true);
            paneDelete.setVisible(false);
        }
    }

    @FXML
    void onContactTyped(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void onDeleteAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        if(lblSupId.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this ?",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> reault = alert.showAndWait();
            if(reault.get()==ButtonType.YES){
                //SupplierModal.deleteSupp(lblSupId.getText());
                supplierBO.deleteSupplier(lblSupId.getText());
                setSupplierTable();
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted !").show();
            }
        }
    }

    @FXML
    void onNameTyped(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void txtSearchAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        search();
    }
    public boolean isMatchContact() {
        Pattern pattern = Pattern.compile("^(07)([0-9]{8,8})$");
        Matcher matcher = pattern.matcher(txtContact.getText());

        boolean isMatchContact = matcher.matches();

        if (!isMatchContact) {
            lblContactWarning.setText("Invalid Input.");
            txtContact.clear();
            txtContact.requestFocus();
        } else {
            lblContactWarning.setText("");
        }
        return isMatchContact;
    }
    public void search() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> list = supplierBO.getSupplierDetail();
        ObservableList<SupplierDTO> data = FXCollections.observableArrayList();

        data.addAll(list);

        FilteredList<SupplierDTO> filteredList = new FilteredList(data, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(supplier -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (supplier.getSupID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (supplier.getSupName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (supplier.getSupEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(supplier.getSupContact()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<SupplierDTO> sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tblSupplier.comparatorProperty());
        tblSupplier.setItems(sortedList);
    }
    public void getAddress(SupplierDTO newValue){

    }
}
