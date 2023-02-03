package lk.ijse.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StoreManageFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<ItemDTO> tblItem;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemType;

    @FXML
    private TableColumn<?, ?> colItemQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCode;

    @FXML
    private Label lblType;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblPrice;

    @FXML
    private ImageView imgUpdate;

    @FXML
    private ProgressIndicator prgCircle;

    @FXML
    private Pane panDelete;

    @FXML
    private ImageView imgUpdate1;

    @FXML
    private Label lblAddNewItem;

    @FXML
    private Pane paneUpdate;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TextField txtNewQty;

    @FXML
    private TextField txtNewPrice;

    @FXML
    private ImageView imgUpdate11;

    @FXML
    private Label lblPriceWarning;

    @FXML
    private Label lblQtyWarning;

    @FXML
    private TextField txtSearch;
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        setItemTable();
        selectedItem();
    }
    public void selectedItem(){
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (null!= newValue){
                        setData(newValue);
                    }
                }
        );
    }
    private void setData(ItemDTO newValue) {
        lblCode.setText(newValue.getItemID());
        lblType.setText(newValue.getDetail());
        lblQty.setText(String.valueOf(newValue.getQty()));
        lblPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtNewQty.setText(String.valueOf(newValue.getQty()));
        txtNewPrice.setText(String.valueOf(newValue.getUnitPrice()));

    }
    public void setCellValueFactory(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemID"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("detail"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }
    public void setItemTable() throws SQLException, ClassNotFoundException {
        ObservableList<ItemDTO> data = FXCollections.observableArrayList();
        //ArrayList<ItemD> itemList = ItemModal.getDetail();
        ArrayList<ItemDTO> itemList = itemBO.getItemDetail();
        data.addAll(itemList);
        tblItem.setItems(data);

    }

    @FXML
    void OnKeyTypePrice(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void btnUpdateAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(isMatchQty() & isMatchPrice()){
            int newQty = Integer.parseInt(txtNewQty.getText());
            double newprice = Double.parseDouble(txtNewPrice.getText());

            Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure whether do you want to UPDATE ?",
                    ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.YES){
                //Item item = new Item(lblCode.getText(),newQty,"",newprice);
                ItemDTO item = new ItemDTO(lblCode.getText(),newQty,"",newprice);
                try {
                    //ItemModal.updateData(item);
                    itemBO.updateItem(item);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }finally {
                    paneUpdate.setVisible(false);
                    prgCircle.setVisible(false);
                    imgUpdate.setVisible(true);
                    panDelete.setVisible(true);
                    txtNewPrice.clear();
                    txtNewQty.clear();
                    setItemTable();
                }
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again !");
        }
    }

    @FXML
    void imgSearchAction(MouseEvent event) {
        txtSearch.requestFocus();
    }

    @FXML
    void lblAddItemAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.ADD_ITEM, pane);
    }

    @FXML
    void onCancelAction(MouseEvent event) {
        paneUpdate.setVisible(false);
        panDelete.setVisible(true);
        prgCircle.setVisible(false);
        imgUpdate.setVisible(true);
    }

    @FXML
    void onDeleteAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        if(lblCode.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this ?",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> reault = alert.showAndWait();
            if(reault.get()==ButtonType.YES){
                //ItemModal.deleteItem(lblCode.getText());
                itemBO.deleteItem(lblCode.getText());
                setItemTable();
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted !").show();
            }
        }
    }

    @FXML
    void onKeyTypeQty(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void onUpdateAction(MouseEvent event) {
        if(!lblCode.getText().equals("")){
            imgUpdate.setVisible(false);
            prgCircle.setVisible(true);
            panDelete.setVisible(false);
            paneUpdate.setVisible(true);
        }else{
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }
    }

    @FXML
    void txtSearchAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        search();
    }
    public boolean isMatchQty(){
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(txtNewQty.getText());

        boolean isMatchQty = matcher.matches();

        if(!isMatchQty){
            lblQtyWarning.setText("Invalid Input.");
            txtNewQty.clear();
            txtNewQty.requestFocus();
        }else{
            lblQtyWarning.setText("");
        }
        return  isMatchQty;
    }
    public boolean isMatchPrice(){
        Pattern pattern = Pattern.compile("^[0-9]+\\.[0-9]{2}$");
        Matcher matcher = pattern.matcher(txtNewPrice.getText());

        boolean isMatchAmount = matcher.matches();

        if (!isMatchAmount) {
            lblPriceWarning.setText("Invalid Input.");
            txtNewPrice.clear();
            txtNewPrice.requestFocus();
        } else {
            lblPriceWarning.setText("");
        }
        return isMatchAmount;
    }
    public void search() throws SQLException, ClassNotFoundException {
        //ArrayList<Item> list = ItemModal.getDetail();
        ArrayList<ItemDTO> list = itemBO.getItemDetail();
        ObservableList<ItemDTO> data = FXCollections.observableArrayList();

        data.addAll(list);

        FilteredList<ItemDTO> filteredList = new FilteredList(data, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (item.getItemID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (item.getDetail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<ItemDTO> sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tblItem.comparatorProperty());
        tblItem.setItems(sortedList);
    }
}

