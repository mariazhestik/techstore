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
        loadView("/com/example/techstore/Views/CustomersView.fxml");
    }

    @FXML
    private void handleManageDeliveries(ActionEvent event) {
        loadView("/com/example/techstore/Views/DeliveriesView.fxml");
    }

    @FXML
    private void handleManageStoreInventory(ActionEvent event) {
        loadView("/com/example/techstore/Views/store_inventory.fxml");
    }

    @FXML
    private void handleManageBrands(ActionEvent event) {
        loadView("/com/example/techstore/Views/BrandsView.fxml");
    }

    @FXML
    private void handleManageEmployees(ActionEvent event) {
        loadView("/com/example/techstore/Views/EmployeesView.fxml");
    }

    // Метод для загрузки вида продуктов сразу после аутентификации
    public void loadProductsView() {
        loadView("/com/example/techstore/Views/ProductsView.fxml");
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
