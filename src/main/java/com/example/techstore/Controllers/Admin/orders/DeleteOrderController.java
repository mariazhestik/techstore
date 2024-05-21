package com.example.techstore.Controllers.Admin.orders;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Orders;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteOrderController {

    @FXML
    private Label orderLabel;

    @FXML
    private Button deleteButton;

    private Orders order;

    public void setOrder(Orders order) {
        this.order = order;
        orderLabel.setText("Order ID: " + order.getOrderId() + ", Date: " + order.getDate() + ", Status: " + order.getStatus());
    }

    @FXML
    private void handleDeleteOrder() {
        String query = "DELETE FROM orders WHERE order_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getOrderId());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Order deleted successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error deleting order: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Order Deletion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }
}
