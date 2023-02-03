package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;

import static javafx.scene.paint.Color.RED;

public class LoggingFormController {
    public Label lblPassWarning;
    public AnchorPane pane;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;

    public void txtUserNameAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws IOException {
        btnLoginAction(actionEvent);
    }

    public void btnLoginAction(ActionEvent actionEvent) throws IOException {
        if(txtUserName.getText().equals("")){
            if(txtPassword.getText().equals("")){
                Navigation.navigate(Routes.ADMIN,pane);
            }else{
                txtPassword.setFocusColor(RED);
                lblPassWarning.setText("Invalid Password");
            }
        } else  {
            txtUserName.setFocusColor(RED);
            txtPassword.setFocusColor(RED);
            lblPassWarning.setText("Invalid Password or UserName.");
        }
    }
}
