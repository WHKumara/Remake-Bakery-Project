package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.bo.custom.PurchaseOrderBO;
import lk.ijse.db.DBConnection;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.Order;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;
import lk.ijse.view.tm.OrderTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderFormController {

    public TextField txtContact;
    public ComboBox cmbCustomer;
    public ComboBox cmbDescription;
    public JFXButton btnPlaceOrder;
    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblCustID;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyHand;

    @FXML
    private Label lblDesc;

    @FXML
    private Label lblName;

    @FXML
    private Label lblContact;

    @FXML
    private TableView<OrderTm> tblOrder;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colDetail;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private Label lblTotal;

    @FXML
    private ComboBox<String> cmbItem;

    @FXML
    private Label lblItemId;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtAmount;

    @FXML
    private Label lblBalance;
    
    

    @FXML
    private Label lblQtyWaning;
    private ObservableList<OrderTm> obList = FXCollections.observableArrayList();

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);
    @FXML
    private Label lblAmountWarning;
    public void initialize() {
        loadCustomerId();
        loadItemId();
        loadItemNames();
        loadNextOrderId();
        setCellValueFactory();

    }
    @FXML
    void btnAddAction(ActionEvent event) {
        if(isMatchQty()){
            boolean isEnough2 = Double.parseDouble(txtQty.getText()) <= Double.parseDouble(lblQtyHand.getText());
            boolean isEnough = Double.parseDouble(lblQtyHand.getText()) >0;

            //check item and customer are selected kiyl
            if(!lblCustID.getText().equals("") && !lblItemId.getText().equals("")){
                if (isEnough && isEnough2){
                    String code = lblItemId.getText();
                    int qty = Integer.parseInt(txtQty.getText());
                    String desc = lblDesc.getText();
                    double unitPrice = Double.parseDouble(lblUnitPrice.getText());
                    double total = unitPrice * qty;
                    Button btnDelete = new Button("Delete");
                    double netTot =  0;
                    txtQty.setText("");

                    if (!obList.isEmpty()) {
                        /* check same item has been in table. If so, update that row instead of adding new row to the table */
                        for (int i = 0; i < tblOrder.getItems().size(); i++) {
                            if (colItemId.getCellData(i).equals(code)) {
                               // qty +=(double) colQty.getCellData(i);
                                total = unitPrice * qty;
                                obList.get(i).setQty(qty);
                                obList.get(i).setPrice(total);
                                tblOrder.refresh();
                                return;
                            }
                        }
                    }

                    /* set delete button to some action before it put on obList */
                    btnDelete.setOnAction((e) -> {
                        ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.orElse(no) == ok) {
                            OrderTm tm = (OrderTm) tblOrder.getSelectionModel().getSelectedItem();
                            tblOrder.getItems().removeAll(tblOrder.getSelectionModel().getSelectedItem());
                            setTotal();
                            txtAmountAction(event);
                        }
                    });
                    obList.add(new OrderTm(code, desc, qty, unitPrice, btnDelete, total));
                    tblOrder.setItems(obList);
                    setTotal();
                    System.out.println(obList);

                }else {
                    //new Alert(Alert.AlertType.WARNING,"Please select item or customer !").show();
                    enoughAlert();
                }
            }else {
                try{
                    new Alert(Alert.AlertType.WARNING,"Please select item or customer !").show();
                }catch (RuntimeException e){
                    throw new RuntimeException(e);
                }

            }
        }
    }

    @FXML
    void btnNewAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.ADD_CUSTOMER, pane);
    }

    @FXML
    void btnPlaceOrderAction(ActionEvent event) {
        if(isMatchAmount()){
            String orderId = lblOrderId.getText();
            String customerId = String.valueOf(lblCustID.getText());
            double amount = Double.parseDouble(lblTotal.getText());

            ArrayList<OrderDetailDTO> orderData = new ArrayList<>();

            /* load all cart items' to orderDetails arrayList */
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                /* get each row details to (PlaceOrderTm)tm in each time and add them to the orderDetails */
                OrderTm tm = obList.get(i);
                orderData.add(new OrderDetailDTO(orderId, tm.getItemId(),tm.getDetail(), tm.getUnitPrice(),tm.getQty()));
            }

            OrderDTO order = new OrderDTO(orderId,customerId,null,null,amount,orderData);
            try {
                if (txtAmountAction(event) && !txtAmount.getText().equals("")){
                   // boolean isPlaced = PlaceOrderModal.placeOrder(order);
                    boolean isPlaced = placeOrder(order);
                    if (isPlaced) {
                        /* to clear table */
                        obList.clear();
                        loadNextOrderId();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order Placed!",ButtonType.OK);
                        Optional<ButtonType> result =alert.showAndWait();
                        if(result.get()==ButtonType.OK){

                            InputStream resourses = this.getClass().getResourceAsStream("/lk/ijse/finalproject/reports/Order.jrxml");

                            HashMap<String, Object> hm = new HashMap<>();
                            hm.put("orderId",lblOrderId.getText());
                            hm.put("custId",lblCustID.getText());
                            hm.put("name",lblName.getText());
                            hm.put("total",Double.parseDouble(lblTotal.getText()));
                            hm.put("amount",txtAmount.getText());
                            hm.put("balance",lblBalance.getText());


                            try {
                                JasperReport jasperReport = JasperCompileManager.compileReport(resourses);
                                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getDBConnection().getConnection());
                                JasperViewer.viewReport(jasperPrint,false);
                                //JasperPrintManager.printReport(jasperPrint,true);
                            } catch (JRException | SQLException | ClassNotFoundException e) {
                                System.out.println(e.toString());
                            }
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
                    }
                }else {
                    new Alert(Alert.AlertType.WARNING, "Amount is not enough !").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void cmbCustomerAction(ActionEvent event) {

        String code = String.valueOf(cmbCustomer.getValue());
        try {
            //Customer cust = CustomerModal.search(code);
            CustomerDTO cust = customerBO.searchCustomer(code);
            fillCustomerFields(cust);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemAction(ActionEvent event) {

        String code = String.valueOf(cmbItem.getValue());
        try {
            //Item item = ItemModal.search(code);
            ItemDTO item = itemBO.searchItem(code);
            fillItemFields(item);
            txtQty.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    boolean txtAmountAction(ActionEvent event) {
        double balance = Double.parseDouble(txtAmount.getText())-Double.parseDouble(lblTotal.getText());
        lblBalance.setText(String.valueOf(balance));
        if(balance<0){
            txtAmount.requestFocus();
            return false;
        }
        return true;
    }

    @FXML
    void txtQtyAction(ActionEvent event) {
        btnAddAction(event);
        setTotal();
        //enoughAlert();
    }

    public void enoughAlert(){
        double qtyHand = Double.parseDouble(lblQtyHand.getText());
        double qty = Double.parseDouble(txtQty.getText());
        if(qtyHand<qty){
            new Alert(Alert.AlertType.WARNING,"Quantity is not enough !").show();
            txtQty.requestFocus();
        }
    }
    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory("itemId"));
        colDetail.setCellValueFactory(new PropertyValueFactory("detail"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colOption.setCellValueFactory(new PropertyValueFactory("btn"));
    }
    public void setTotal(){
        tblOrder.refresh();
        double total = 0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            total+= Double.parseDouble(String.valueOf(colPrice.getCellData(i)));
            lblTotal.setText(String.valueOf(total));
        }
        if (tblOrder.getItems().size()==0){
            lblTotal.setText("0.0");
        }
    }
   private void loadCustomerId() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            //ArrayList<String> idList = CustomerModal.loadCustId();
            ArrayList<String> idList = customerBO.loadCustomerId();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbCustomer.setItems(observableList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemId() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            //ArrayList<String> idList = ItemModal.loadItemId();
            ArrayList<String> idList = itemBO.loadItemId();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbItem.setItems(observableList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void fillItemFields(ItemDTO item) {
        lblItemId.setText(item.getItemID());
        lblDesc.setText(item.getDetail());
        lblQtyHand.setText(String.valueOf(item.getQty()));
        lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
    }
    private void fillCustomerFields(CustomerDTO cust) {
        lblCustID.setText(cust.getCustID());
        txtContact.setText("0"+ cust.getCustContact());
        lblName.setText(cust.getCustName());
    }

    private void loadNextOrderId() {
        try {
            //String orderId = OrderModal.generateNextOrderId();
            String orderId = purchaseOrderBO.generateNextOrderID();
            lblOrderId.setText(orderId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isMatchQty() {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(txtQty.getText());

        boolean isMatchPrice = matcher.matches();

        if (!isMatchPrice) {
            lblQtyWaning.setText("Invalid Input.");
            txtQty.clear();
            txtQty.requestFocus();
        } else {
            lblQtyWaning.setText("");
        }
        return isMatchPrice;
    }

    public boolean isMatchAmount(){
        Pattern pattern = Pattern.compile("^[0-9]+\\.[0-9]{2}$");
        Matcher matcher = pattern.matcher(txtAmount.getText());

        boolean isMatchAmount = matcher.matches();

        if (!isMatchAmount) {
            lblAmountWarning.setText("Invalid Input.");
            txtAmount.clear();
            txtAmount.requestFocus();
        } else {
            lblAmountWarning.setText("");
        }
        return isMatchAmount;
    }
    private  boolean placeOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException{
        try {
            DBConnection.getDBConnection().getConnection().setAutoCommit(false);
           //boolean isOrderAdded = purchaseOrderBO.save(new Order(orderDTO.getID(),orderDTO.getCustID(),null,null,orderDTO.getAmount(),orderDTO.getOrderDetailDTO()));
            boolean isOrderAdded = purchaseOrderBO.save(orderDTO);
            if (isOrderAdded) {
               // boolean isUpdated = ItemModal.updateQty(order.getOrderDetails());
                boolean isUpdated = itemBO.updateItemQty(orderDTO.getOrderDetailDTO());
                if (isUpdated) {
                    //boolean isOrderDetailAdded = OrderDetailModal.saveOrderDetails(order.getOrderDetails());
                    boolean isOrderDetailAdded = purchaseOrderBO.saveOrderDetails(orderDTO.getOrderDetailDTO());
                    if (isOrderDetailAdded) {
                        DBConnection.getDBConnection().getConnection().commit();
                        return true;
                    }
                }
            }
            DBConnection.getDBConnection().getConnection().rollback();
            return false;
        } finally {
            DBConnection.getDBConnection().getConnection().setAutoCommit(true);
        }
    }

    public void txtContactAction(ActionEvent actionEvent) {
        String contact = txtContact.getText();
        try {
            //Customer cust = CustomerModal.search(code);
            CustomerDTO cust = customerBO.searchCustomer(contact);
            fillCustomerFields(cust);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbDescriptionAction(ActionEvent actionEvent) {
        String name = String.valueOf(cmbDescription.getValue());
        try {
            //Item item = ItemModal.search(code);
            ItemDTO item = itemBO.searchItem(name);
            fillItemFields(item);
            txtQty.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadItemNames(){
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            //ArrayList<String> idList = ItemModal.loadItemId();
            ArrayList<String> idList = itemBO.loadItemName();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbDescription.setItems(observableList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

