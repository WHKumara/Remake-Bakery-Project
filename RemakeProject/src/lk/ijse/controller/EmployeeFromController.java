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
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.db.DBConnection;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;
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

public class EmployeeFromController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<EmployeeDTO> tblEmpDetail;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colEmpAddress;

    @FXML
    private TableColumn<?, ?> colEmpContact;

    @FXML
    private TableColumn<?, ?> colEmpEmail;

    @FXML
    private Label lblPrint;

    @FXML
    private Label lblId;

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
    private Pane paneUpdate;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private Label lblContactWarning;

    @FXML
    private TextField txtSearch;
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize() throws SQLException, ClassNotFoundException {
        tblEmpDetail.refresh();
        setCellValueFactory();
        setEmpTable();
        selectedItem();
    }
    public void setCellValueFactory() {

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("EmpID"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("EmpName"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("EmpContact"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("EmpAddress"));
        colEmpEmail.setCellValueFactory(new PropertyValueFactory<>("EmpEmail"));

    }
    public void setEmpTable() throws SQLException, ClassNotFoundException {
        ObservableList<EmployeeDTO> data = FXCollections.observableArrayList();

        ArrayList<EmployeeDTO> emp = employeeBO.getEmployeeDetail();

        data.addAll(emp);

        tblEmpDetail.setItems(data);
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
                EmployeeDTO emp = new EmployeeDTO(lblId.getText(),name,contact,address,null);
                try {
                  //  EmployeeModal.updateEmployee(emp);
                    employeeBO.updateEmployee(emp);
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
                    setEmpTable();
                }
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again !");
        }    }

    @FXML
    void imgCloseAction(MouseEvent event) {
        paneUpdate.setVisible(false);
        paneDelete.setVisible(true);
        prgCircle.setVisible(false);
        imgUpdate.setVisible(true);
    }

    @FXML
    void imgPrintAction(MouseEvent event) {
        InputStream resourses = this.getClass().getResourceAsStream("/lk/ijse/finalproject/reports/EmployeeReport.jrxml");

        try {
            JasperReport report = JasperCompileManager.compileReport(resourses);
            JasperPrint print = JasperFillManager.fillReport(report,null, DBConnection.getDBConnection().getConnection());
            JasperViewer.viewReport(print,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    void imgSearchAction(MouseEvent event) {
        txtSearch.requestFocus();
    }

    @FXML
    void lblAddEmployeeAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.ADD_EMPLOYEE,pane);
    }

    @FXML
    void onAddressTypeAction(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void onContactTypedAction(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void onDeleteAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        if(lblId.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this ?",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> reault = alert.showAndWait();
            if(reault.get()==ButtonType.YES){
               // EmployeeModal.deleteEmployee(lblId.getText());
                employeeBO.deleteEmployee(lblId.getText());
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted !").show();
                setEmpTable();
            }
        }
    }

    @FXML
    void onNameTypedAction(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void onUpdateAction(MouseEvent event) {
        if(!lblId.getText().equals("")){
            imgUpdate.setVisible(false);
            prgCircle.setVisible(true);
            paneDelete.setVisible(false);
            paneUpdate.setVisible(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }
    }

    @FXML
    void txtSearchAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        search();
    }
    public void selectedItem(){
        tblEmpDetail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (null!= newValue){
                        setData(newValue);
                    }
                }
        );
    }
    private void setData(EmployeeDTO newValue) {
        lblId.setText(newValue.getEmpID());
        lblName.setText(newValue.getEmpName());
        lblContact.setText(String.valueOf(newValue.getEmpContact()));
        txtAddress.setText(newValue.getEmpAddress());
        txtName.setText(newValue.getEmpName());
        txtContact.setText(String.valueOf(newValue.getEmpContact()));
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
        //ArrayList<Employee> list = EmployeeModal.getDetails();
        ArrayList<EmployeeDTO> list = employeeBO.getEmployeeDetail();
        ObservableList<EmployeeDTO> data = FXCollections.observableArrayList();

        data.addAll(list);

        FilteredList<EmployeeDTO> filteredList = new FilteredList(data, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (employee.getEmpID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (employee.getEmpName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(employee.getEmpContact()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<EmployeeDTO> sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tblEmpDetail.comparatorProperty());
        tblEmpDetail.setItems(sortedList);
    }
}
