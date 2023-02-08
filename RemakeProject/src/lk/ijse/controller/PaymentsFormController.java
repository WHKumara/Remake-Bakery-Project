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
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.db.DBConnection;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;
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

public class PaymentsFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<PaymentDTO> tblPayments;

    @FXML
    private TableColumn<?, ?> colPayId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblSuppId;

    @FXML
    private Label lblDate;

    @FXML
    private ImageView icnUpdate;

    @FXML
    private Pane updatePane;

    @FXML
    private TextField txtAmount;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbSuppId;

    @FXML
    private Label lblSupId;

    @FXML
    private Label lblAmountWarning;

    @FXML
    private ProgressIndicator prgCircle;

    @FXML
    private TextField txtSearch;
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        setPaymentTable();
        loadSupplierIds();
        selectedItem();
    }
    public void setCellValueFactory(){
        colPayId.setCellValueFactory(new PropertyValueFactory<>("PaymentID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("PaymentAmount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("SupID"));
    }
    public void setPaymentTable() throws SQLException, ClassNotFoundException {
        ObservableList<PaymentDTO> data = FXCollections.observableArrayList();
        ArrayList<PaymentDTO> paymentlList =paymentBO.getPaymentDetail();
        data.addAll(paymentlList);
        tblPayments.setItems(data);

    }
    private void loadSupplierIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            //ArrayList<String> idList = SupplierModal.loadSupId();
            ArrayList<String> idList = supplierBO.loadSupId();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbSuppId.setItems(observableList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectedItem(){
        tblPayments.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (null!= newValue){
                        setData((PaymentDTO) newValue);
                    }
                }
        );
    }
    private void setData(PaymentDTO newValue) {
        lblPaymentId.setText(newValue.getPaymentID());
        lblAmount.setText(String.valueOf(newValue.getPaymentAmount()));
        lblDate.setText(String.valueOf(newValue.getDate()));
        lblSuppId.setText(newValue.getSupID());
        txtAmount.setText(String.valueOf(newValue.getPaymentAmount()));
        lblSupId.setText(newValue.getSupID());
    }

    @FXML
    void addClickAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.ADD_PAYMENT,pane);
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        if(isMatch()){
            double amount = Double.parseDouble(txtAmount.getText());
            String supId = lblSupId.getText();

            Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure whether do you want to UPDATE ?",
                    ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.YES){
                //Payment payment = new Payment(lblPaymentId.getText(),amount,null,null,supId);
                PaymentDTO payment = new PaymentDTO(lblPaymentId.getText(),amount,null,null,supId);
                try {
                    //PaymentModal.updateData(payment);
                    paymentBO.updateData(payment);
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION,"Details are Updated !",ButtonType.OK);
                    Optional<ButtonType> result2 = alert2.showAndWait();
                    if(result2.get()==ButtonType.OK){
                        updatePane.setVisible(false);
                        icnUpdate.setVisible(true);
                        prgCircle.setVisible(false);
                        txtAmount.clear();
                        lblSupId.setText("");
                        setPaymentTable();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again !").show();
        }

    }

    @FXML
    void cmbSuppIdAction(ActionEvent event) {
        lblSupId.setText((String) cmbSuppId.getValue());
        btnUpdate.setVisible(true);
    }

    @FXML
    void imgPrintAction(MouseEvent event) {
        InputStream resourses = this.getClass().getResourceAsStream("/lk/ijse/finalproject/reports/PaymentReport.jrxml");

        try {
            JasperReport report = JasperCompileManager.compileReport(resourses);
            JasperPrint print = JasperFillManager.fillReport(report,null, DBConnection.getDBConnection().getConnection());
            JasperViewer.viewReport(print,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void imgSearchAction(MouseEvent event) {
        txtSearch.requestFocus();
    }

    @FXML
    void onCancelAction(MouseEvent event) {
        updatePane.setVisible(false);
        prgCircle.setVisible(false);
        icnUpdate.setVisible(true);
    }

    @FXML
    void onKeyTyped(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void txtSearchAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        search();
    }

    @FXML
    void updateClickAction(MouseEvent event) {
        if(!lblPaymentId.getText().equals("")){
            TextInputDialog tiDialog = new TextInputDialog();
            tiDialog.setTitle("password conformation");
            tiDialog.setHeaderText("Please enter your Password :");
            tiDialog.setContentText("Pssword: ");

            Optional<String> result = tiDialog.showAndWait();

            if(result.get().equals("admin")){
                updatePane.setVisible(true);
                icnUpdate.setVisible(false);
                prgCircle.setVisible(true);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING,"Password is incorrect !",ButtonType.OK);
                alert.show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Please select a row in table !").show();
        }
    }
    public boolean isMatch() {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(txtAmount.getText());

        boolean isMatchPrice = matcher.matches();

        if (!isMatchPrice) {
            lblAmountWarning.setText("Invalid Input.");
            txtAmount.clear();
            txtAmount.requestFocus();
        } else {
            lblAmountWarning.setText("");
        }
        return isMatchPrice;
    }
    public void search() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDTO> list = paymentBO.getPaymentDetail();
        ObservableList<PaymentDTO> data = FXCollections.observableArrayList();

        data.addAll(list);

        FilteredList<PaymentDTO> filteredList = new FilteredList(data, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(payment -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (payment.getPaymentID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (payment.getSupID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(payment.getDate()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<PaymentDTO> sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tblPayments.comparatorProperty());
        tblPayments.setItems(sortedList);
    }
}
