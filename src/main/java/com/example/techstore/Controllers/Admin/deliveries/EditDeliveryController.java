package com.example.techstore.Controllers.Admin.deliveries;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Delivery;
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

public class EditDeliveryController {

    @FXML
    private ComboBox<String> productIdComboBox;
    @FXML
    private DatePicker deliveryDatePicker;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> employeeIdComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Delivery delivery;

    @FXML
    public void initialize() {
        loadProductIds();
        loadEmployeeIds();
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        productIdComboBox.setValue(delivery.getProductId() + " - " + getProductName(delivery.getProductId()));
        deliveryDatePicker.setValue(java.time.LocalDate.parse(delivery.getDeliveryDate()));
        quantityField.setText(String.valueOf(delivery.getQuantity()));
        employeeIdComboBox.setValue(delivery.getEmployeeId() + " - " + getEmployeeName(delivery.getEmployeeId()));
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
    private void handleSaveDelivery() {
        try {
            int productId = extractId(productIdComboBox.getValue());
            int employeeId = extractId(employeeIdComboBox.getValue());
            String deliveryDate = deliveryDatePicker.getValue().toString();
            int quantity = Integer.parseInt(quantityField.getText());

            String query = "UPDATE delivery SET product_id = ?, date = ?, quantity = ?, employee_id = ? WHERE delivery_id = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, productId);
                statement.setString(2, deliveryDate);
                statement.setInt(3, quantity);
                statement.setInt(4, employeeId);
                statement.setInt(5, delivery.getDeliveryId());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Delivery updated successfully!");
                    closeWindow();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error updating delivery: " + e.getMessage());
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

    private String getProductName(int productId) {
        String query = "SELECT name FROM product WHERE product_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getEmployeeName(int employeeId) {
        String query = "SELECT name FROM employee WHERE employee_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Delivery Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
