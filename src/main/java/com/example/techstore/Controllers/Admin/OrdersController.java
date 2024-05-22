package com.example.techstore.Controllers.Admin;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Orders;
import com.example.techstore.Controllers.Admin.orders.DeleteOrderController;
import com.example.techstore.Controllers.Admin.orders.EditOrderController;
import com.example.techstore.Controllers.Admin.orders.ViewOrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersController {

    @FXML
    private TableView<Orders> ordersTableView;
    @FXML
    private TableColumn<Orders, Integer> orderIdColumn;
    @FXML
    private TableColumn<Orders, Integer> customerIdColumn;
    @FXML
    private TableColumn<Orders, Integer> employeeIdColumn;
    @FXML
    private TableColumn<Orders, Integer> productIdColumn;
    @FXML
    private TableColumn<Orders, String> dateColumn;
    @FXML
    private TableColumn<Orders, Double> totalAmountColumn;
    @FXML
    private TableColumn<Orders, String> statusColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button addButton;

    private ObservableList<Orders> ordersList;
    private FilteredList<Orders> filteredData;

    @FXML
    public void initialize() {
        ordersList = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(ordersList, p -> true);

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        SortedList<Orders> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ordersTableView.comparatorProperty());

        ordersTableView.setItems(sortedData);

        loadOrders();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(order -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (order.getDate().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(order.getOrderId()).contains(lowerCaseFilter) ||
                        order.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
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
            loadOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEditOrder() {
        Orders selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
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
                loadOrders();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToViewOrder() {
        Orders selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
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
        Orders selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
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
                loadOrders();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
