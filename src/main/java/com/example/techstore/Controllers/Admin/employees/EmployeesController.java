package com.example.techstore.Controllers.Admin.employees;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeesController {

    @FXML
    private ListView<Employee> employeesListView;
    @FXML
    private Button addButton;
    @FXML
    private TextField searchField;

    private ObservableList<Employee> employeesList;
    private FilteredList<Employee> filteredData;

    @FXML
    public void initialize() {
        employeesList = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(employeesList, p -> true);

        employeesListView.setItems(filteredData);
        loadEmployees();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (employee.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(employee.getEmployeeId()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadEmployees() {
        employeesList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employee";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");

                Employee employee = new Employee(employeeId, name);
                employeesList.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToAddEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/AddEmployee.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Employee");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Reload employees after adding new employee
            loadEmployees();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEditEmployee() {
        Employee selectedEmployee = employeesListView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/EditEmployee.fxml"));
                Parent root = loader.load();

                EditEmployeeController controller = loader.getController();
                controller.setEmployee(selectedEmployee);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Employee");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload employees after editing
                loadEmployees();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToViewEmployee() {
        Employee selectedEmployee = employeesListView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/ViewEmployee.fxml"));
                Parent root = loader.load();

                ViewEmployeeController controller = loader.getController();
                controller.setEmployee(selectedEmployee);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("View Employee");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToDeleteEmployee() {
        Employee selectedEmployee = employeesListView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/DeleteEmployee.fxml"));
                Parent root = loader.load();

                DeleteEmployeeController controller = loader.getController();
                controller.setEmployee(selectedEmployee);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Delete Employee");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload employees after deleting
                loadEmployees();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
