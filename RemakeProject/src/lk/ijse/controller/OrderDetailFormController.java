package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class OrderDetailFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<?> tblOrderDetail;

    @FXML
    private TableColumn<?, ?> coleOrderId;

    @FXML
    private TableColumn<?, ?> colCostId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private Label lblIncome;

    @FXML
    private TextField txtSearch;

    @FXML
    void imgBackAction(MouseEvent event) {

    }

    @FXML
    void imgSearchAction(MouseEvent event) {

    }

    @FXML
    void txtSearchAction(KeyEvent event) {

    }

}
