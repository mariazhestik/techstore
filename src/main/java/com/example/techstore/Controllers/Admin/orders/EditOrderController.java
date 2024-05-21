package com.example.techstore.Controllers.Admin.orders;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Orders;
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

public class EditOrderController {

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

    private Orders order;

    @FXML
    public void initialize() {
        loadCustomerIds();
        loadEmployeeIds();
        loadProductIds();
        loadStatusOptions();
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

    public void setOrder(Orders order) {
        this.order = order;
        customerIdComboBox.setValue(order.getCustomerId() + " - " + getCustomerName(order.getCustomerId()));
        employeeIdComboBox.setValue(order.getEmployeeId() + " - " + getEmployeeName(order.getEmployeeId()));
        productIdComboBox.setValue(order.getProductId() + " - " + getProductName(order.getProductId()));
        datePicker.setValue(java.time.LocalDate.parse(order.getDate()));
        totalAmountField.setText(String.valueOf(order.getTotalAmount()));
        statusComboBox.setValue(order.getStatus());
    }

    private String getCustomerName(int customerId) {
        String query = "SELECT name FROM customer WHERE customer_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customerId);
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

    @FXML
    private void handleSaveOrder() {
        try {
            int customerId = extractId(customerIdComboBox.getValue());
            int employeeId = extractId(employeeIdComboBox.getValue());
            int productId = extractId(productIdComboBox.getValue());
            String date = datePicker.getValue().toString();
            double totalAmount = Double.parseDouble(totalAmountField.getText());
            String status = statusComboBox.getValue();

            String query = "UPDATE orders SET customer_id = ?, employee_id = ?, product_id = ?, date = ?, total_amount = ?, status = ? WHERE order_id = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, customerId);
                statement.setInt(2, employeeId);
                statement.setInt(3, productId);
                statement.setString(4, date);
                statement.setDouble(5, totalAmount);
                statement.setString(6, status);
                statement.setInt(7, order.getOrderId());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Order updated successfully!");
                    closeWindow();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error updating order: " + e.getMessage());
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
        alert.setTitle("Order Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) customerIdComboBox.getScene().getWindow();
        stage.close();
    }
}
