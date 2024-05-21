package com.example.techstore.Controllers.Admin;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Orders;
import com.example.techstore.Controllers.Admin.orders.DeleteOrderController;
import com.example.techstore.Controllers.Admin.orders.EditOrderController;
import com.example.techstore.Controllers.Admin.orders.ViewOrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersController {

    @FXML
    private ListView<Orders> ordersListView;
    @FXML
    private Button addButton;

    private ObservableList<Orders> ordersList;

    @FXML
    public void initialize() {
        ordersList = FXCollections.observableArrayList();
        loadOrders();
    }

    private void loadOrders() {
        ordersList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM orders";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                int employeeId = resultSet.getInt("employee_id");
                int productId = resultSet.getInt("product_id");
                String date = resultSet.getString("date");
                double totalAmount = resultSet.getDouble("total_amount");
                String status = resultSet.getString("status");

                Orders order = new Orders(orderId, customerId, employeeId, productId, date, totalAmount, status);
                ordersList.add(order);
            }

            ordersListView.setItems(ordersList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToAddOrder() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/AddOrder.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Order");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Reload orders after adding new order
            loadOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEditOrder() {
        Orders selectedOrder = ordersListView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/EditOrder.fxml"));
                Parent root = loader.load();

                EditOrderController controller = loader.getController();
                controller.setOrder(selectedOrder);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Order");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload orders after editing
                loadOrders();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToViewOrder() {
        Orders selectedOrder = ordersListView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/ViewOrder.fxml"));
                Parent root = loader.load();

                ViewOrderController controller = loader.getController();
                controller.setOrder(selectedOrder);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("View Order");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToDeleteOrder() {
        Orders selectedOrder = ordersListView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/DeleteOrder.fxml"));
                Parent root = loader.load();

                DeleteOrderController controller = loader.getController();
                controller.setOrder(selectedOrder);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Delete Order");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload orders after deleting
                loadOrders();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
