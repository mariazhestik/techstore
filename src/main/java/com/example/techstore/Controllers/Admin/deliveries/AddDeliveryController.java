package com.example.techstore.Controllers.Admin.deliveries;

import com.example.techstore.Database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDeliveryController {

    @FXML
    private TextField productIdField;
    @FXML
    private DatePicker deliveryDatePicker;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField employeeIdField;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void handleAddDelivery() {
        String productId = productIdField.getText();
        String deliveryDate = deliveryDatePicker.getValue().toString();
        String quantity = quantityField.getText();
        String employeeId = employeeIdField.getText();

        String query = "INSERT INTO delivery (product_id, date, quantity, employee_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, Integer.parseInt(productId));
            statement.setString(2, deliveryDate);
            statement.setInt(3, Integer.parseInt(quantity));
            statement.setInt(4, Integer.parseInt(employeeId));

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Delivery added successfully!");
                closeWindow();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error adding delivery: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Delivery Addition");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
