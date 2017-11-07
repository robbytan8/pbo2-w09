package com.robby.controller;

import com.robby.MainApp;
import com.robby.utility.ViewUtil;
import com.robby.ws.Department;
import com.robby.ws.DepartmentWs;
import com.robby.ws.DepartmentWs_Service;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Robby
 */
public class MainFormController implements Initializable {

    @FXML
    private BorderPane borderPane;
    private DepartmentWs departmentWs;
    private ObservableList<Department> departments;
    private Stage departmentStage;
    private DepartmentWs_Service departmentService;

    public DepartmentWs getDepartmentWs() {
        if (departmentWs == null) {
            departmentWs = getDepartmentService().getDepartmentWsPort();
        }
        return departmentWs;
    }

    public DepartmentWs_Service getDepartmentService() {
        if (departmentService == null) {
            departmentService = new DepartmentWs_Service();
        }
        return departmentService;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public ObservableList<Department> getDepartments() {
        try {
            if (departments == null) {
                departments = FXCollections.observableArrayList();
                departments.addAll(getDepartmentWs().getAllDepartment());
            }
            return departments;
        } catch (Exception e) {
            ViewUtil.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            Platform.exit();
        }
        return null;
    }

    @FXML
    private void mnAboutAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Created by Robby");
        alert.setTitle("Developer Info");
        alert.showAndWait();
    }

    @FXML
    private void mnCloseAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void mnReportAllStudentAction(ActionEvent event) {
        //  TODO print report
    }

    @FXML
    private void toolDepAction(ActionEvent event) {
        if (departmentStage == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/DepartmentForm.fxml"));
                AnchorPane root = loader.load();
                DepartmentFormController controller = loader.getController();
                controller.setMainController(this);
                Scene scene = new Scene(root);
                departmentStage = new Stage();
                departmentStage.setScene(scene);
                departmentStage.setTitle("Department Management");
                departmentStage.initOwner(borderPane.getScene().getWindow());
                departmentStage.initModality(Modality.NONE);
            } catch (IOException ex) {
                ViewUtil.showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        }
        if (departmentStage.isShowing() && !departmentStage.isFocused()) {
            departmentStage.toFront();
        } else {
            departmentStage.show();
        }
    }

    @FXML
    private void toolStudentAction(ActionEvent event) {
    }

}
