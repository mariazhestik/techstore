package com.example.techstore.Controllers.Admin.orders;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Orders;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditOrderController {

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

    private Orders order;

    public void setOrder(Orders order) {
        this.order = order;
        customerIdField.setText(String.valueOf(order.getCustomerId()));
        employeeIdField.setText(String.valueOf(order.getEmployeeId()));
        productIdField.setText(String.valueOf(order.getProductId()));
        dateField.setText(order.getDate());
        totalAmountField.setText(String.valueOf(order.getTotalAmount()));
        statusField.setText(order.getStatus());
    }

    @FXML
    private void handleSaveOrder() {
        int customerId = Integer.parseInt(customerIdField.getText());
        int employeeId = Integer.parseInt(employeeIdField.getText());
        int productId = Integer.parseInt(productIdField.getText());
        String date = dateField.getText();
        double totalAmount = Double.parseDouble(totalAmountField.getText());
        String status = statusField.getText();

        String query = "UPDATE orders SET customer_id = ?, employee_id = ?, product_id = ?, date = ?, total_amount = ?, status = ? WHERE order_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);
            statement.setInt(2, employeeId);
            statement.setInt(3, productId);
            statement.setString(4, date);
            statement.setDouble(5, totalAmount);
            statement.setString(6, status);
            statement.setInt(7, order.getOrderId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Order updated successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error updating order: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Order Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) customerIdField.getScene().getWindow();
        stage.close();
    }
}
