package com.example.techstore.Controllers.Admin.products;

import com.example.techstore.Database.DatabaseConnection;
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

public class AddProductController {

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> brandIdComboBox;
    @FXML
    private TextField priceField;
    @FXML
    private ComboBox<String> processorComboBox;
    @FXML
    private ComboBox<String> memoryComboBox;
    @FXML
    private ComboBox<String> ramComboBox;
    @FXML
    private ComboBox<String> screenTypeComboBox;
    @FXML
    private ComboBox<String> materialOfCorpsComboBox;
    @FXML
    private ComboBox<String> colourComboBox;
    @FXML
    private ComboBox<String> dimensionComboBox;
    @FXML
    private ComboBox<String> batteryComboBox;
    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    public void initialize() {
        loadBrandIds();
        loadProcessors();
        loadMemoryOptions();
        loadRamOptions();
        loadScreenTypes();
        loadMaterialOfCorpsOptions();
        loadColourOptions();
        loadStatusOptions();
        loadDimensionOptions();
        loadBatteryOptions();
    }

    private void loadBrandIds() {
        ObservableList<String> brandIds = FXCollections.observableArrayList();
        String query = "SELECT brand_id, name FROM brand";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int brandId = resultSet.getInt("brand_id");
                String name = resultSet.getString("name");
                brandIds.add(brandId + " - " + name);
            }
            brandIdComboBox.setItems(brandIds);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading brand IDs: " + e.getMessage());
        }
    }

    private void loadProcessors() {
        ObservableList<String> processors = FXCollections.observableArrayList("Snapdragon 865", "A14 Bionic", "Kirin 990", "Snapdragon 888");
        processorComboBox.setItems(processors);
    }

    private void loadMemoryOptions() {
        ObservableList<String> memoryOptions = FXCollections.observableArrayList("128GB", "256GB", "512GB");
        memoryComboBox.setItems(memoryOptions);
    }

    private void loadRamOptions() {
        ObservableList<String> ramOptions = FXCollections.observableArrayList("4GB", "8GB", "12GB");
        ramComboBox.setItems(ramOptions);
    }

    private void loadScreenTypes() {
        ObservableList<String> screenTypes = FXCollections.observableArrayList("AMOLED", "OLED", "IPS LCD");
        screenTypeComboBox.setItems(screenTypes);
    }

    private void loadMaterialOfCorpsOptions() {
        ObservableList<String> materialOptions = FXCollections.observableArrayList("Glass", "Aluminum", "Plastic");
        materialOfCorpsComboBox.setItems(materialOptions);
    }

    private void loadColourOptions() {
        ObservableList<String> colourOptions = FXCollections.observableArrayList("Black", "White", "Blue", "Gray", "Green");
        colourComboBox.setItems(colourOptions);
    }

    private void loadStatusOptions() {
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Available", "Out of Stock", "Discontinued");
        statusComboBox.setItems(statusOptions);
    }

    private void loadDimensionOptions() {
        ObservableList<String> dimensionOptions = FXCollections.observableArrayList("6.7\"", "6.8\"", "6.5\"");
        dimensionComboBox.setItems(dimensionOptions);
    }

    private void loadBatteryOptions() {
        ObservableList<String> batteryOptions = FXCollections.observableArrayList("4500mAh", "4300mAh", "4000mAh");
        batteryComboBox.setItems(batteryOptions);
    }

    @FXML
    private void handleAddProduct() {
        String name = nameField.getText();
        int brandId = extractId(brandIdComboBox.getValue());
        double price = Double.parseDouble(priceField.getText());
        String processor = processorComboBox.getValue();
        String memory = memoryComboBox.getValue();
        String ram = ramComboBox.getValue();
        String screenType = screenTypeComboBox.getValue();
        String materialOfCorps = materialOfCorpsComboBox.getValue();
        String colour = colourComboBox.getValue();
        String dimension = dimensionComboBox.getValue();
        String battery = batteryComboBox.getValue();
        String status = statusComboBox.getValue();

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
                showAlert(Alert.AlertType.INFORMATION, "Product added successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error adding product: " + e.getMessage());
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
