package com.example.techstore.Controllers.Admin.brands;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Brand;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBrandController {

    @FXML
    private Label brandLabel;

    @FXML
    private Button deleteButton;

    private Brand brand;

    public void setBrand(Brand brand) {
        this.brand = brand;
        brandLabel.setText("Brand ID: " + brand.getBrandId() + ", Name: " + brand.getName());
    }

    @FXML
    private void handleDeleteBrand() {
        String query = "DELETE FROM brand WHERE brand_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, brand.getBrandId());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Brand deleted successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error deleting brand: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Brand Deletion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }
}
