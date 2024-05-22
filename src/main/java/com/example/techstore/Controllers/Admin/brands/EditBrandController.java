package com.example.techstore.Controllers.Admin.brands;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Brand;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditBrandController {

    @FXML
    private TextField nameField;

    private Brand brand;

    public void setBrand(Brand brand) {
        this.brand = brand;
        nameField.setText(brand.getName());
    }

    @FXML
    private void handleSaveBrand() {
        String name = nameField.getText();

        String query = "UPDATE brand SET name = ? WHERE brand_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setInt(2, brand.getBrandId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Brand updated successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error updating brand: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Brand Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
