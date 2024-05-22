package com.example.techstore.Controllers.Admin.orders;

import com.example.techstore.Database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddOrderController {

    @FXML
    private ComboBox<String> customerIdComboBox;
    @FXML
    private ComboBox<String> employeeIdComboBox;
    @FXML
    private ComboBox<String> productIdComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField totalAmountField;
    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    public void initialize() {
        loadCustomerIds();
        loadEmployeeIds();
        loadProductIds();
        loadStatusOptions();

        productIdComboBox.setOnAction(event -> handleProductSelection());
    }

    private void loadCustomerIds() {
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        String query = "SELECT customer_id, name FROM customer";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                customerIds.add(customerId + " - " + name);
            }
            customerIdComboBox.setItems(customerIds);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading customer IDs: " + e.getMessage());
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

    private void loadStatusOptions() {
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Completed", "Processing", "Pending");
        statusComboBox.setItems(statusOptions);
    }

    private void handleProductSelection() {
        String selectedProduct = productIdComboBox.getValue();
        if (selectedProduct != null) {
            int productId = extractId(selectedProduct);
            String query = "SELECT price FROM product WHERE product_id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, productId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    double price = resultSet.getDouble("price");
                    totalAmountField.setText(String.valueOf(price));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error retrieving product price: " + e.getMessage());
            }
        }
    }

    private boolean isProductAvailable(int productId) {
        String query = "SELECT quantity, status FROM store_inventory JOIN product ON store_inventory.product_id = product.product_id WHERE store_inventory.product_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int quantity = resultSet.getInt("quantity");
                String status = resultSet.getString("status");
                return quantity > 0 && !status.equals("Out of Stock") && !status.equals("Discontinued");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error checking product availability: " + e.getMessage());
        }
        return false;
    }

    @FXML
    private void handleAddOrder() {
        try {
            int customerId = extractId(customerIdComboBox.getValue());
            int employeeId = extractId(employeeIdComboBox.getValue());
            int productId = extractId(productIdComboBox.getValue());
            String date = datePicker.getValue().toString();
            double totalAmount = Double.parseDouble(totalAmountField.getText());
            String status = statusComboBox.getValue();

            if (!isProductAvailable(productId)) {
                showAlert(Alert.AlertType.ERROR, "Product is not available for order.");
                return;
            }

            String query = "INSERT INTO orders (customer_id, employee_id, product_id, date, total_amount, status) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, customerId);
                statement.setInt(2, employeeId);
                statement.setInt(3, productId);
                statement.setString(4, date);
                statement.setDouble(5, totalAmount);
                statement.setString(6, status);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Order added successfully!");
                    closeWindow();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error adding order: " + e.getMessage());
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
        alert.setTitle("Order Addition");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) customerIdComboBox.getScene().getWindow();
        stage.close();
    }
}
