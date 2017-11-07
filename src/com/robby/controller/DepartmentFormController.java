package com.robby.controller;

import com.robby.utility.TextUtil;
import com.robby.utility.ViewUtil;
import com.robby.ws.Department;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Robby
 */
public class DepartmentFormController implements Initializable {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableColumn<Department, String> colCode;
    @FXML
    private TableColumn<Department, String> colName;
    @FXML
    private TableColumn<Department, Boolean> colStatus;
    private MainFormController mainController;
    @FXML
    private TableView<Department> tableDepartment;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    private Department selectedDepartment;

    @FXML
    private void btnDeleteAction(ActionEvent event) {
        //  TODO Delete
    }

    @FXML
    private void btnResetAction(ActionEvent event) {
        this.resetFieldAndButton();
    }

    @FXML
    private void btnSaveAction(ActionEvent event) {
        if (!TextUtil.isEmptyField(txtCode, txtName)) {
            Department department = new Department();
            department.setCode(txtCode.getText().trim());
            department.setName(txtName.getText().trim());
            mainController.getDepartmentWs().addDepartment(txtCode.getText(), txtName.getText());
            mainController.getDepartments().clear();
            mainController.getDepartments().addAll(mainController.getDepartmentWs().getAllDepartment());
            txtCode.clear();
            txtName.clear();
        } else {
            ViewUtil.showAlert(Alert.AlertType.ERROR, "Error", "Please fill all field");
        }
    }

    @FXML
    private void btnUpdateAction(ActionEvent event) {
        //  TODO update
    }

    public void setMainController(MainFormController mainController) {
        this.mainController = mainController;
        this.tableDepartment.setItems(mainController.getDepartments());
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Department, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Department, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().isStatus());
            }
        });
        colStatus.setCellFactory(CheckBoxTableCell.forTableColumn(colStatus));
        txtCode.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                String newValue) -> {
            if (!newValue.matches("\\d+")) {
                if (((StringProperty) observable).getValue() == null
                        || ((StringProperty) observable).getValue().isEmpty()) {
                    oldValue = "";
                }
                ((StringProperty) observable).setValue(oldValue);
            }
        });
    }

    @FXML
    private void tableDepartmentMouseClicked(MouseEvent event) {
        selectedDepartment = tableDepartment.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            txtCode.setDisable(true);
            txtCode.setText(selectedDepartment.getCode());
            txtName.setText(selectedDepartment.getName());
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
        }
    }

    private void resetFieldAndButton() {
        txtCode.setDisable(false);
        txtCode.clear();
        txtName.clear();
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        selectedDepartment = null;
    }
}
