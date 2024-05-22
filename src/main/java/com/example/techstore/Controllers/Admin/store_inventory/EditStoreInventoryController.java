package com.example.techstore.Controllers.Admin.store_inventory;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.StoreInventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditStoreInventoryController {

    @FXML
    private ComboBox<String> productIdComboBox;
    @FXML
    private TextField quantityField;

    private StoreInventory storeInventory;

    public void setStoreInventory(StoreInventory storeInventory) {
        this.storeInventory = storeInventory;
        productIdComboBox.setValue(String.valueOf(storeInventory.getProductId()));
        quantityField.setText(String.valueOf(storeInventory.getQuantity()));
    }

    @FXML
    public void initialize() {
        loadProductIds();
    }

    private void loadProductIds() {
        ObservableList<String> productIds = FXCollections.observableArrayList();
        String query = "SELECT product_id, name FROM product";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                productIds.add(productId + " - " + name);
            }
            productIdComboBox.setItems(productIds);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading product IDs: " + e.getMessage());
        }
    }

    @FXML
    private void handleSaveStoreInventory() {
        try {
            int productId = extractId(productIdComboBox.getValue());
            int quantity = Integer.parseInt(quantityField.getText());

            String query = "UPDATE store_inventory SET product_id = ?, quantity = ? WHERE store_inventory_id = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, productId);
                statement.setInt(2, quantity);
                statement.setInt(3, storeInventory.getStoreInventoryId());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Store inventory updated successfully!");
                    closeWindow();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error updating store inventory: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid input: " + e.getMessage());
        }
    }

    private int extractId(String comboBoxValue) {
        if (comboBoxValue != null && comboBoxValue.contains(" - ")) {
            return Integer.parseInt(comboBoxValue.split(" - ")[0]);
        } else {
            return Integer.parseInt(comboBoxValue);
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Store Inventory Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) productIdComboBox.getScene().getWindow();
        stage.close();
    }
}
