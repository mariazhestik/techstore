package com.example.techstore.Controllers.Admin.store_inventory;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.StoreInventory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStoreInventoryController {

    @FXML
    private Label productIdLabel;

    @FXML
    private Button deleteButton;

    private StoreInventory storeInventory;

    public void setStoreInventory(StoreInventory storeInventory) {
        this.storeInventory = storeInventory;
        productIdLabel.setText(String.valueOf(storeInventory.getProductId()));
    }

    @FXML
    private void handleDeleteStoreInventory() {
        String query = "DELETE FROM store_inventory WHERE store_inventory_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, storeInventory.getStoreInventoryId());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(AlertType.INFORMATION, "Store inventory deleted successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error deleting store inventory: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Store Inventory Deletion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }
}
