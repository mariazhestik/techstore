package com.example.techstore.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button manageProductsButton;

    @FXML
    private Button manageOrdersButton;

    @FXML
    private Button manageCustomersButton;

    @FXML
    private Button manageDeliveriesButton;

    @FXML
    void handleManageProducts(ActionEvent event) {
        switchToView("/com/example/techstore/Views/ProductsView.fxml", "Manage Products");
    }

    @FXML
    void handleManageOrders(ActionEvent event) {
        showAlert("Manage Orders", "Here you can manage orders.");
    }

    @FXML
    void handleManageCustomers(ActionEvent event) {
        showAlert("Manage Customers", "Here you can manage customers.");
    }

    @FXML
    void handleManageDeliveries(ActionEvent event) {
        showAlert("Manage Deliveries", "Here you can manage deliveries.");
    }

    private void switchToView(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) manageProductsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the view.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
