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
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.db.DBConnection;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<CustomerDTO> tblCustomer;

    @FXML
    private TableColumn<?, ?> colCustId;

    @FXML
    private TableColumn<?, ?> colCustName;

    @FXML
    private TableColumn<?, ?> colCustAddress;

    @FXML
    private TableColumn<?, ?> colCustContact;

    @FXML
    private Label lblSelectedId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblContact;

    @FXML
    private ImageView icnUpdate;

    @FXML
    private ProgressIndicator prgCircle;

    @FXML
    private Pane updatePane;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ImageView icnUpdate11;

    @FXML
    private Label lblContactWarning;

    @FXML
    private Pane paneDelete;

    @FXML
    private ImageView icnUpdate1;

    @FXML
    private TextField txtSearch;
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        setCusotmerTable();
        selectedItem();
    }
    public void setCellValueFactory(){
        colCustId.setCellValueFactory(new PropertyValueFactory<>("CustID"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("CustName"));
        colCustContact.setCellValueFactory(new PropertyValueFactory<>("CustContact"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("CustAddress"));
    }
    public void setCusotmerTable() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerDTO> data = FXCollections.observableArrayList();
        //ArrayList<Customer> custList = CustomerModal.getDetail();
        ArrayList<CustomerDTO> custList =customerBO.getCustomerDetail();
        data.addAll(custList);
        tblCustomer.setItems(data);
    }
    @FXML
    void addClickAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.ADD_CUSTOMER,pane);
    }

    @FXML
    void btnUpdateAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(isMatchContact()){
            String name = txtName.getText();
            int contact = Integer.parseInt(txtContact.getText());
            String address = txtAddress.getText();

            Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure whether do you want to UPDATE ?",
                    ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.YES){
                CustomerDTO cust = new CustomerDTO(lblSelectedId.getText(),name,contact,address);
                try {
                    //CustomerModal.updateData(cust);
                    customerBO.updateData(cust);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }finally {
                    updatePane.setVisible(false);
                    prgCircle.setVisible(false);
                    icnUpdate.setVisible(true);
                    paneDelete.setVisible(true);
                    txtName.clear();
                    txtContact.clear();
                    txtAddress.clear();
                    setCusotmerTable();
                }
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again !").show();
        }
    }

    @FXML
    void clickDeleteAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        if(lblSelectedId.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this ?",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> reault = alert.showAndWait();
            if(reault.get()==ButtonType.YES){
                //CustomerModal.deleteCustomer(lblSelectedId.getText());
                customerBO.deleteCustomer(lblSelectedId.getText());
                setCusotmerTable();
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted !").show();
            }
        }
    }

    @FXML
    void imgPrintAction(MouseEvent event) {
        InputStream resourses = this.getClass().getResourceAsStream("/lk/ijse/finalproject/reports/CustomerReport.jrxml");

        try {
            JasperReport report = JasperCompileManager.compileReport(resourses);
            JasperPrint print = JasperFillManager.fillReport(report,null, DBConnection.getDBConnection().getConnection());
            JasperViewer.viewReport(print,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        /*try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resourses);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getDBConnection().getConnection());
            JasperViewer.viewReport(jasperPrint);
            //JasperPrintManager.printReport(jasperPrint,true);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
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
        updatePane.setVisible(false);
        prgCircle.setVisible(false);
        icnUpdate.setVisible(true);
        paneDelete.setVisible(true);
    }

    @FXML
    void onContactTyped(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void onNameTyped(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void txtSearchAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        search();
    }

    @FXML
    void updateClickAction(MouseEvent event) {
        if(lblSelectedId.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }else {
            icnUpdate.setVisible(false);
            prgCircle.setVisible(true);
            updatePane.setVisible(true);
            paneDelete.setVisible(false);
        }
    }
    public void selectedItem(){
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (null!= newValue){
                        setData((CustomerDTO) newValue);
                    }
                }
        );
    }
    private void setData(CustomerDTO newValue) {
        lblSelectedId.setText(newValue.getCustID());
        lblName.setText(newValue.getCustName());
        lblAddress.setText(newValue.getCustAddress());
        lblContact.setText("0"+newValue.getCustContact());
        txtName.setText(newValue.getCustName());
        txtContact.setText("0"+newValue.getCustContact());
        txtAddress.setText(newValue.getCustAddress());
    }
    public boolean isMatchContact() {
        Pattern pattern = Pattern.compile("^(07)([0-9]{8})$");
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
       // ArrayList<Customer> list = CustomerModal.getDetail();
        ArrayList<CustomerDTO> list = customerBO.getCustomerDetail();
        ObservableList<CustomerDTO> data = FXCollections.observableArrayList();

        data.addAll(list);

        FilteredList<CustomerDTO> filteredList = new FilteredList(data, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (customer.getCustID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (customer.getCustName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(customer.getCustContact()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<CustomerDTO> sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tblCustomer.comparatorProperty());
        tblCustomer.setItems(sortedList);
    }

}
