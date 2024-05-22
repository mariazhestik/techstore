package com.example.techstore.Controllers.Admin.employees;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditEmployeeController {

    @FXML
    private TextField nameField;

    private Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
        nameField.setText(employee.getName());
    }

    @FXML
    private void handleSaveEmployee() {
        String name = nameField.getText();

        String query = "UPDATE employee SET name = ? WHERE employee_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setInt(2, employee.getEmployeeId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Employee updated successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error updating employee: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Employee Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
