package lk.ijse.controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.navigation.Navigation;
import lk.ijse.navigation.Routes;

import java.io.IOException;

public class HomeFormController {
    public AnchorPane pane;

    public void imgOrderAction(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.ORDER_DETAIL,pane);
    }

    public void imgCustomerAction(MouseEvent mouseEvent) {
    }
}
