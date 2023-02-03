package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.SuperBO;
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

public class AddMaterialFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label lblItemCode;

    @FXML
    private TextField txtMaterialType;

    @FXML
    private TextField txtQuantity;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private ComboBox<String> cmbSupplier;

    @FXML
    private Label lblQtyWarning;
    RawMaterialBO materialBO = (RawMaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MATERIAL);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize() throws SQLException, ClassNotFoundException {
        loadSupplierIds();
        loadNextMaterialId();

    }

    @FXML
    void backAction(MouseEvent event) throws IOException {
        Navigation.navigate(Routes.RAW_MATERIAL,pane);
    }

    @FXML
    void btnAddAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(isMatchQty()){
            if(!txtMaterialType.getText().equals("")){
                String type = txtMaterialType.getText();
                int  qty = Integer.parseInt(txtQuantity.getText());
                String supId = (String) cmbSupplier.getValue();

                Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure whether do you want to ADD this ?",
                        ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get()==ButtonType.YES){
                    //RawMaterialModal.addItem(new RawMaterial(lblItemCode.getText(),type,qty,supId));
                    materialBO.saveMaterial(new RawMaterialDTO(lblItemCode.getText(),type,qty,supId));
                    Alert alert1 = new Alert(Alert.AlertType.WARNING, "Item is Added !.",
                            ButtonType.OK);
                    Optional<ButtonType> result1 = alert1.showAndWait();
                    if (result1.get()==ButtonType.OK){
                        loadNextMaterialId();
                        clearTextFeild();
                    }
                }
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
            Alert alert1 = new Alert(Alert.AlertType.WARNING, "Material is not Added !.",
                    ButtonType.OK);
            Optional<ButtonType> result1 = alert1.showAndWait();
            if (result1.get()==ButtonType.OK)
                Navigation.navigate(Routes.RAW_MATERIAL,pane);
        }
    }
    private void clearTextFeild() {
        txtMaterialType.clear();
        txtQuantity.clear();
    }

    private void loadSupplierIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            //ArrayList<String> idList = SupplierModal.loadSupId();
            ArrayList<String> idList = supplierBO.loadSupId();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbSupplier.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadNextMaterialId() {
        try {
            //String materialId = RawMaterialModal.generateNextMterialId();
            String materialId = materialBO.generateMaterialID();
            lblItemCode.setText(materialId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
}
