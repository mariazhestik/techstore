package com.example.techstore.Controllers.Admin.products;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Product;
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

public class ProductsController {

    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, Integer> brandIdColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> processorColumn;
    @FXML
    private TableColumn<Product, String> memoryColumn;
    @FXML
    private TableColumn<Product, String> ramColumn;
    @FXML
    private TableColumn<Product, String> screenTypeColumn;
    @FXML
    private TableColumn<Product, String> materialOfCorpsColumn;
    @FXML
    private TableColumn<Product, String> colourColumn;
    @FXML
    private TableColumn<Product, String> dimensionColumn;
    @FXML
    private TableColumn<Product, String> batteryColumn;
    @FXML
    private TableColumn<Product, String> statusColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button addButton;

    private ObservableList<Product> productsList;
    private FilteredList<Product> filteredData;

    @FXML
    public void initialize() {
        productsList = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(productsList, p -> true);

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        brandIdColumn.setCellValueFactory(new PropertyValueFactory<>("brandId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        processorColumn.setCellValueFactory(new PropertyValueFactory<>("processor"));
        memoryColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<>("ram"));
        screenTypeColumn.setCellValueFactory(new PropertyValueFactory<>("screenType"));
        materialOfCorpsColumn.setCellValueFactory(new PropertyValueFactory<>("materialOfCorps"));
        colourColumn.setCellValueFactory(new PropertyValueFactory<>("colour"));
        dimensionColumn.setCellValueFactory(new PropertyValueFactory<>("dimension"));
        batteryColumn.setCellValueFactory(new PropertyValueFactory<>("battery"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Создаем SortedList, которая оборачивает FilteredList
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productsTableView.comparatorProperty());

        productsTableView.setItems(sortedData);

        loadProducts();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(product.getProductId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (product.getProcessor().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadProducts() {
        productsList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM product";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                int brandId = resultSet.getInt("brand_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String processor = resultSet.getString("processor");
                String memory = resultSet.getString("memory");
                String ram = resultSet.getString("RAM");
                String screenType = resultSet.getString("screenType");
                String materialOfCorps = resultSet.getString("materialOfCorps");
                String colour = resultSet.getString("colour");
                String dimension = resultSet.getString("dimension");
                String battery = resultSet.getString("Battery");
                String status = resultSet.getString("status");

                Product product = new Product(productId, brandId, name, price, processor, memory, ram, screenType, materialOfCorps, colour, dimension, battery, status);
                productsList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToAddProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/add_product.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Product");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Reload products after adding new product
            loadProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEditProduct() {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/EditProduct.fxml"));
                Parent root = loader.load();

                EditProductController controller = loader.getController();
                controller.setProduct(selectedProduct);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Product");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload products after editing
                loadProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToViewProduct() {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/ViewProduct.fxml"));
                Parent root = loader.load();

                ViewProductController controller = loader.getController();
                controller.setProduct(selectedProduct);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("View Product");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToDeleteProduct() {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/DeleteProduct.fxml"));
                Parent root = loader.load();

                DeleteProductController controller = loader.getController();
                controller.setProduct(selectedProduct);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Delete Product");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload products after deleting
                loadProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
