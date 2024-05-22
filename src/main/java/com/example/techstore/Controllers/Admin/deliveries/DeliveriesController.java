package com.example.techstore.Controllers.Admin.deliveries;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Delivery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveriesController {

    @FXML
    private TableView<Delivery> deliveriesTableView;
    @FXML
    private TableColumn<Delivery, Integer> deliveryIdColumn;
    @FXML
    private TableColumn<Delivery, Integer> productIdColumn;
    @FXML
    private TableColumn<Delivery, String> deliveryDateColumn;
    @FXML
    private TableColumn<Delivery, Integer> quantityColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button addButton;

    private ObservableList<Delivery> deliveriesList;
    private FilteredList<Delivery> filteredData;

    @FXML
    public void initialize() {
        deliveriesList = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(deliveriesList, p -> true);

        deliveryIdColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        deliveryDateColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        SortedList<Delivery> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(deliveriesTableView.comparatorProperty());

        deliveriesTableView.setItems(sortedData);

        loadDeliveries();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(delivery -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(delivery.getDeliveryId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(delivery.getProductId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (delivery.getDeliveryDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadDeliveries() {
        deliveriesList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM delivery";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int deliveryId = resultSet.getInt("delivery_id");
                int productId = resultSet.getInt("product_id");
                String deliveryDate = resultSet.getString("date");
                int quantity = resultSet.getInt("quantity");

                Delivery delivery = new Delivery(deliveryId, productId, deliveryDate, quantity);
                deliveriesList.add(delivery);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToAddDelivery() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/AddDelivery.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Delivery");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Reload deliveries after adding new delivery
            loadDeliveries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEditDelivery() {
        Delivery selectedDelivery = deliveriesTableView.getSelectionModel().getSelectedItem();
        if (selectedDelivery != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/EditDelivery.fxml"));
                Parent root = loader.load();

                EditDeliveryController controller = loader.getController();
                controller.setDelivery(selectedDelivery);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Delivery");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload deliveries after editing
                loadDeliveries();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToViewDelivery() {
        Delivery selectedDelivery = deliveriesTableView.getSelectionModel().getSelectedItem();
        if (selectedDelivery != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/ViewDelivery.fxml"));
                Parent root = loader.load();

                ViewDeliveryController controller = loader.getController();
                controller.setDelivery(selectedDelivery);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("View Delivery");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToDeleteDelivery() {
        Delivery selectedDelivery = deliveriesTableView.getSelectionModel().getSelectedItem();
        if (selectedDelivery != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/DeleteDelivery.fxml"));
                Parent root = loader.load();

                DeleteDeliveryController controller = loader.getController();
                controller.setDelivery(selectedDelivery);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Delete Delivery");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload deliveries after deleting
                loadDeliveries();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
