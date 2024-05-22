package com.example.techstore.Controllers.Admin.store_inventory;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.StoreInventory;
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

public class StoreInventoryController {

    @FXML
    private TableView<StoreInventory> storeInventoryTableView;
    @FXML
    private TableColumn<StoreInventory, Integer> storeInventoryIdColumn;
    @FXML
    private TableColumn<StoreInventory, Integer> productIdColumn;
    @FXML
    private TableColumn<StoreInventory, Integer> quantityColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button addButton;

    private ObservableList<StoreInventory> storeInventoryList;
    private FilteredList<StoreInventory> filteredData;

    @FXML
    public void initialize() {
        storeInventoryList = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(storeInventoryList, p -> true);

        storeInventoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("storeInventoryId"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        SortedList<StoreInventory> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(storeInventoryTableView.comparatorProperty());

        storeInventoryTableView.setItems(sortedData);

        loadStoreInventory();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(storeInventory -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(storeInventory.getProductId()).contains(lowerCaseFilter) ||
                        String.valueOf(storeInventory.getStoreInventoryId()).contains(lowerCaseFilter) ||
                        String.valueOf(storeInventory.getQuantity()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadStoreInventory() {
        storeInventoryList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM store_inventory";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int storeInventoryId = resultSet.getInt("store_inventory_id");
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");

                StoreInventory storeInventory = new StoreInventory(storeInventoryId, productId, quantity);
                storeInventoryList.add(storeInventory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToAddStoreInventory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/AddStoreInventory.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Store Inventory");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Reload store inventory after adding new entry
            loadStoreInventory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEditStoreInventory() {
        StoreInventory selectedStoreInventory = storeInventoryTableView.getSelectionModel().getSelectedItem();
        if (selectedStoreInventory != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/EditStoreInventory.fxml"));
                Parent root = loader.load();

                EditStoreInventoryController controller = loader.getController();
                controller.setStoreInventory(selectedStoreInventory);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Store Inventory");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload store inventory after editing
                loadStoreInventory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToDeleteStoreInventory() {
        StoreInventory selectedStoreInventory = storeInventoryTableView.getSelectionModel().getSelectedItem();
        if (selectedStoreInventory != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/DeleteStoreInventory.fxml"));
                Parent root = loader.load();

                DeleteStoreInventoryController controller = loader.getController();
                controller.setStoreInventory(selectedStoreInventory);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Delete Store Inventory");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload store inventory after deleting
                loadStoreInventory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
