package com.example.techstore.Controllers.Admin.customers;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteCustomerController {

    @FXML
    private Label customerLabel;

    @FXML
    private Button deleteButton;

    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customerLabel.setText("Customer ID: " + customer.getCustomerId() + ", Name: " + customer.getName());
    }

    @FXML
    private void handleDeleteCustomer() {
        String query = "DELETE FROM customer WHERE customer_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, customer.getCustomerId());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Customer deleted successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error deleting customer: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Customer Deletion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }
}
