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
import lk.ijse.bo.custom.RawMaterialBO;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dto.RawMaterialDTO;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RawMaterialFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<RawMaterialDTO> tblRawMaterial;

    @FXML
    private TableColumn<?, ?> colMterialId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private Label lblMaterialId;

    @FXML
    private Label lblType;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblSupId;

    @FXML
    private Label lblUpdateDetails;

    @FXML
    private ImageView imgUpdate;

    @FXML
    private ProgressIndicator prgCircle;

    @FXML
    private Pane paneDelete;

    @FXML
    private ImageView imgUpdate1;

    @FXML
    private Pane updatePane;

    @FXML
    private TextField txtQty;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbSupId;

    @FXML
    private Label lblNewSupId;

    @FXML
    private ImageView imgUpdate11;

    @FXML
    private Label lblQtyWarning;

    @FXML
    private TextField txtSearch;
    RawMaterialBO materialBO = (RawMaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MATERIAL);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        setMaterialTable();
        selectedItem();
        loadSupplierIds();

    }
    public void setCellValueFactory(){
        colMterialId.setCellValueFactory(new PropertyValueFactory<>("MaterialID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("SupID"));
    }

    public void setMaterialTable() throws SQLException, ClassNotFoundException {
        ObservableList<RawMaterialDTO> data = FXCollections.observableArrayList();
        ArrayList<RawMaterialDTO> materialList = materialBO.getMaterialDetail();
        data.addAll(materialList);
        tblRawMaterial.setItems(data);

        System.out.println(data);
    }
    public void selectedItem(){
        tblRawMaterial.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (null!= newValue){
                        setData((RawMaterialDTO) newValue);
                    }
                }
        );
    }
    private void setData(RawMaterialDTO newValue) {
        lblMaterialId.setText(newValue.getMaterialID());
        lblType.setText(newValue.getType());
        lblQty.setText(String.valueOf(newValue.getQty()));
        lblSupId.setText(newValue.getSupID());
        txtQty.setText(String.valueOf(newValue.getQty()));
        lblNewSupId.setText(newValue.getSupID());
    }

    @FXML
    void btnUpdateAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(isMatch()){
            int newQty = Integer.parseInt(txtQty.getText());
            String newSupId = lblNewSupId.getText();

            Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure whether do you want to UPDATE ?",
                    ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.YES){
               // RawMaterial material = new RawMaterial(lblMaterialId.getText(),lblType.getText(),newQty,newSupId);
                RawMaterialDTO material = new RawMaterialDTO(lblMaterialId.getText(),lblType.getText(),newQty,newSupId);
                try {
                   // RawMaterialModal.updateData(material);
                    materialBO.updateMaterialData(material);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }finally {
                    updatePane.setVisible(false);
                    prgCircle.setVisible(false);
                    imgUpdate.setVisible(true);
                    paneDelete.setVisible(true);
                    txtQty.clear();
                    lblNewSupId.setText("");
                    setMaterialTable();
                }
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Try again !");
        }

    }

    @FXML
    void cmbOnAction(ActionEvent event) {
        lblNewSupId.setText((String) cmbSupId.getValue());
        btnUpdate.setVisible(true);
    }

    @FXML
    void imgSearchAction(MouseEvent event) {
        txtSearch.requestFocus();
    }

    @FXML
    void onAddMaterialAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.ADD_MATERIAL,pane);
    }

    @FXML
    void onCancelAction(MouseEvent event) {
        updatePane.setVisible(false);
        paneDelete.setVisible(true);
        prgCircle.setVisible(false);
        imgUpdate.setVisible(true);
    }

    @FXML
    void onClickDelete(MouseEvent event) throws SQLException, ClassNotFoundException {
        if(lblMaterialId.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this ?",ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> reault = alert.showAndWait();
            if(reault.get()==ButtonType.YES){
               // RawMaterialModal.deleteMateril(lblMaterialId.getText());
                materialBO.deleteMaterial(lblMaterialId.getText());
                setMaterialTable();
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted !").show();
            }
        }
    }

    @FXML
    void onClickUpdate(MouseEvent event) {
        if(lblMaterialId.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,"Please select row in table !").show();
        }else {
            imgUpdate.setVisible(false);
            prgCircle.setVisible(true);
            updatePane.setVisible(true);
            paneDelete.setVisible(false);
        }
    }

    @FXML
    void onKeyTyped(KeyEvent event) {
        btnUpdate.setVisible(true);
    }

    @FXML
    void txtSearchAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        search();
    }
    private void loadSupplierIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            //ArrayList<String> idList = SupplierModal.loadSupId();
            ArrayList<String> idList = supplierBO.loadSupId();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbSupId.setItems(observableList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isMatch() {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(txtQty.getText());

        boolean isMatchPrice = matcher.matches();

        if (!isMatchPrice) {
            lblQtyWarning.setText("Invalid Input.");
            txtQty.clear();
            txtQty.requestFocus();
        } else {
            lblQtyWarning.setText("");
        }
        return isMatchPrice;
    }
    public void search() throws SQLException, ClassNotFoundException {
        ArrayList<RawMaterialDTO> list = materialBO.getMaterialDetail();
        ObservableList<RawMaterialDTO> data = FXCollections.observableArrayList();

        data.addAll(list);

        FilteredList<RawMaterialDTO> filteredList = new FilteredList(data, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(material -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (material.getMaterialID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (material.getType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<RawMaterialDTO> sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tblRawMaterial.comparatorProperty());
        tblRawMaterial.setItems(sortedList);
    }
}
