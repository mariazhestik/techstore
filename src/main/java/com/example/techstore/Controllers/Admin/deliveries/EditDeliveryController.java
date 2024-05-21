package com.example.techstore.Controllers.Admin.deliveries;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Delivery;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditDeliveryController {

    @FXML
    private TextField productIdField;
    @FXML
    private DatePicker deliveryDatePicker;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField employeeIdField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Delivery delivery;

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        productIdField.setText(String.valueOf(delivery.getProductId()));
        deliveryDatePicker.setValue(java.time.LocalDate.parse(delivery.getDeliveryDate()));
        quantityField.setText(String.valueOf(delivery.getQuantity()));
        employeeIdField.setText(String.valueOf(delivery.getEmployeeId()));
    }

    @FXML
    private void handleSaveDelivery() {
        String productId = productIdField.getText();
        String deliveryDate = deliveryDatePicker.getValue().toString();
        String quantity = quantityField.getText();
        String employeeId = employeeIdField.getText();

        String query = "UPDATE delivery SET product_id = ?, date = ?, quantity = ?, employee_id = ? WHERE delivery_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, Integer.parseInt(productId));
            statement.setString(2, deliveryDate);
            statement.setInt(3, Integer.parseInt(quantity));
            statement.setInt(4, Integer.parseInt(employeeId));
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
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Delivery Update");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
