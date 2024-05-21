package com.example.techstore.Controllers.Admin.customers;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditCustomerController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField addressField;

    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        nameField.setText(customer.getName());
        contactField.setText(customer.getContact());
        addressField.setText(customer.getAddress());
    }

    @FXML
    private void handleSaveCustomer() {
        String name = nameField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();

        String query = "UPDATE customer SET name = ?, contact = ?, address = ? WHERE customer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, contact);
            statement.setString(3, address);
            statement.setInt(4, customer.getCustomerId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Customer updated successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error updating customer: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Customer Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
