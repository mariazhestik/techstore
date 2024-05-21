package com.example.techstore.Controllers.Admin.products;

import com.example.techstore.Database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProductController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField brandIdField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField processorField;
    @FXML
    private TextField memoryField;
    @FXML
    private TextField ramField;
    @FXML
    private TextField screenTypeField;
    @FXML
    private TextField materialOfCorpsField;
    @FXML
    private TextField colourField;
    @FXML
    private TextField dimensionField;
    @FXML
    private TextField batteryField;
    @FXML
    private TextField statusField;

    @FXML
    private void handleAddProduct() {
        String name = nameField.getText();
        int brandId = Integer.parseInt(brandIdField.getText());
        double price = Double.parseDouble(priceField.getText());
        String processor = processorField.getText();
        String memory = memoryField.getText();
        String ram = ramField.getText();
        String screenType = screenTypeField.getText();
        String materialOfCorps = materialOfCorpsField.getText();
        String colour = colourField.getText();
        String dimension = dimensionField.getText();
        String battery = batteryField.getText();
        String status = statusField.getText();

        String query = "INSERT INTO product (brand_id, name, price, processor, memory, RAM, screenType, materialOfCorps, colour, dimension, Battery, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, brandId);
            statement.setString(2, name);
            statement.setDouble(3, price);
            statement.setString(4, processor);
            statement.setString(5, memory);
            statement.setString(6, ram);
            statement.setString(7, screenType);
            statement.setString(8, materialOfCorps);
            statement.setString(9, colour);
            statement.setString(10, dimension);
            statement.setString(11, battery);
            statement.setString(12, status);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(AlertType.INFORMATION, "Product added successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error adding product: " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Product Addition");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
