package com.example.techstore.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardController {

    @FXML
    private VBox contentArea;

    @FXML
    private void handleManageProducts(ActionEvent event) {
        loadView("/com/example/techstore/Views/ProductsView.fxml");
    }

    @FXML
    private void handleManageOrders(ActionEvent event) {
        loadView("/com/example/techstore/Views/OrdersView.fxml");
    }

    @FXML
    private void handleManageCustomers(ActionEvent event) {
        showAlert("Manage Customers", "Here you can manage customers.");
    }

    @FXML
    private void handleManageDeliveries(ActionEvent event) {
        showAlert("Manage Deliveries", "Here you can manage deliveries.");
    }

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
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
