package com.example.techstore.Controllers.Admin.orders;

import com.example.techstore.Database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddOrderController {

    @FXML
    private TextField customerIdField;
    @FXML
    private TextField employeeIdField;
    @FXML
    private TextField productIdField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField totalAmountField;
    @FXML
    private TextField statusField;

    @FXML
    private void handleAddOrder() {
        int customerId = Integer.parseInt(customerIdField.getText());
        int employeeId = Integer.parseInt(employeeIdField.getText());
        int productId = Integer.parseInt(productIdField.getText());
        String date = dateField.getText();
        double totalAmount = Double.parseDouble(totalAmountField.getText());
        String status = statusField.getText();

        String query = "INSERT INTO orders (customer_id, employee_id, product_id, date, total_amount, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);
            statement.setInt(2, employeeId);
            statement.setInt(3, productId);
            statement.setString(4, date);
            statement.setDouble(5, totalAmount);
            statement.setString(6, status);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Order added successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error adding order: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Order Addition");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) customerIdField.getScene().getWindow();
        stage.close();
    }
}
