package com.example.techstore.Controllers.Admin.deliveries;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Delivery;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteDeliveryController {

    @FXML
    private Label deliveryLabel;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;

    private Delivery delivery;

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        deliveryLabel.setText("Delivery ID: " + delivery.getDeliveryId() + ", Date: " + delivery.getDeliveryDate() + ", Quantity: " + delivery.getQuantity());
    }

    @FXML
    private void handleDeleteDelivery() {
        String query = "DELETE FROM delivery WHERE delivery_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, delivery.getDeliveryId());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Delivery deleted successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error deleting delivery: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Delivery Deletion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }
}
