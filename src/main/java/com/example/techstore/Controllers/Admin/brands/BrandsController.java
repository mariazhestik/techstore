package com.example.techstore.Controllers.Admin.brands;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Brand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandsController {

    @FXML
    private ListView<Brand> brandsListView;
    @FXML
    private Button addButton;
    @FXML
    private TextField searchField;

    private ObservableList<Brand> brandsList;
    private FilteredList<Brand> filteredData;

    @FXML
    public void initialize() {
        brandsList = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(brandsList, p -> true);

        brandsListView.setItems(filteredData);
        loadBrands();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(brand -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (brand.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(brand.getBrandId()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadBrands() {
        brandsList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM brand";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int brandId = resultSet.getInt("brand_id");
                String name = resultSet.getString("name");

                Brand brand = new Brand(brandId, name);
                brandsList.add(brand);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToAddBrand() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/AddBrand.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Brand");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Reload brands after adding new brand
            loadBrands();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEditBrand() {
        Brand selectedBrand = brandsListView.getSelectionModel().getSelectedItem();
        if (selectedBrand != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/EditBrand.fxml"));
                Parent root = loader.load();

                EditBrandController controller = loader.getController();
                controller.setBrand(selectedBrand);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Brand");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload brands after editing
                loadBrands();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToViewBrand() {
        Brand selectedBrand = brandsListView.getSelectionModel().getSelectedItem();
        if (selectedBrand != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/ViewBrand.fxml"));
                Parent root = loader.load();

                ViewBrandController controller = loader.getController();
                controller.setBrand(selectedBrand);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("View Brand");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToDeleteBrand() {
        Brand selectedBrand = brandsListView.getSelectionModel().getSelectedItem();
        if (selectedBrand != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/DeleteBrand.fxml"));
                Parent root = loader.load();

                DeleteBrandController controller = loader.getController();
                controller.setBrand(selectedBrand);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Delete Brand");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload brands after deleting
                loadBrands();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
