package com.robby.controller;

import com.robby.ws.ExampleWs;
import com.robby.ws.ExampleWs_Service;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Robby
 */
public class MainFormController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtValue1;
    @FXML
    private TextField txtValue2;
    private ExampleWs_Service service;
    private ExampleWs ws;

    @FXML
    private void btnSubmitAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(ws.hello(txtName.getText()));
        alert.showAndWait();
        txtName.clear();
    }

    @FXML
    private void btnSumAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(ws.add(Integer.parseInt(txtValue1.getText()),
                Integer.parseInt(txtValue2.getText())).toString());
        alert.showAndWait();
        txtValue1.clear();
        txtValue2.clear();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new ExampleWs_Service();
        ws = service.getExampleWsPort();
    }

}
