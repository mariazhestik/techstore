package com.example.techstore.Controllers.Admin.employees;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteEmployeeController {

    @FXML
    private Label employeeLabel;

    @FXML
    private Button deleteButton;

    private Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
        employeeLabel.setText("Employee ID: " + employee.getEmployeeId() + ", Name: " + employee.getName());
    }

    @FXML
    private void handleDeleteEmployee() {
        String query = "DELETE FROM employee WHERE employee_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, employee.getEmployeeId());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Employee deleted successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error deleting employee: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Employee Deletion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }
}
