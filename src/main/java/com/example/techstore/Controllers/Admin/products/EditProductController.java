package com.example.techstore.Controllers.Admin.products;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditProductController {

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

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        nameField.setText(product.getName());
        brandIdField.setText(String.valueOf(product.getBrandId()));
        priceField.setText(String.valueOf(product.getPrice()));
        processorField.setText(product.getProcessor());
        memoryField.setText(product.getMemory());
        ramField.setText(product.getRam());
        screenTypeField.setText(product.getScreenType());
        materialOfCorpsField.setText(product.getMaterialOfCorps());
        colourField.setText(product.getColour());
        dimensionField.setText(product.getDimension());
        batteryField.setText(product.getBattery());
        statusField.setText(product.getStatus());
    }

    @FXML
    private void handleSaveProduct() {
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

        String query = "UPDATE product SET brand_id = ?, name = ?, price = ?, processor = ?, memory = ?, RAM = ?, screenType = ?, materialOfCorps = ?, colour = ?, dimension = ?, Battery = ?, status = ? WHERE product_id = ?";

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
            statement.setInt(13, product.getProductId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(AlertType.INFORMATION, "Product updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error updating product: " + e.getMessage());
        }
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Product Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
