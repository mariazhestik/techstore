package com.example.techstore.Controllers.Admin.deliveries;

import com.example.techstore.Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddDeliveryController {

    @FXML
    private ComboBox<String> productIdComboBox;
    @FXML
    private DatePicker deliveryDatePicker;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> employeeIdComboBox;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    @FXML
    public void initialize() {
        loadProductIds();
        loadEmployeeIds();
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

    private void loadEmployeeIds() {
        ObservableList<String> employeeIds = FXCollections.observableArrayList();
        String query = "SELECT employee_id, name FROM employee";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                String name = resultSet.getString("name");
                employeeIds.add(employeeId + " - " + name);
            }
            employeeIdComboBox.setItems(employeeIds);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading employee IDs: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddDelivery() {
        try {
            int productId = extractId(productIdComboBox.getValue());
            int employeeId = extractId(employeeIdComboBox.getValue());
            String deliveryDate = deliveryDatePicker.getValue().toString();
            int quantity = Integer.parseInt(quantityField.getText());

            String query = "INSERT INTO delivery (product_id, date, quantity, employee_id) VALUES (?, ?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, productId);
                statement.setString(2, deliveryDate);
                statement.setInt(3, quantity);
                statement.setInt(4, employeeId);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Delivery added successfully!");
                    closeWindow();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error adding delivery: " + e.getMessage());
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
        alert.setTitle("Delivery Addition");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
