package com.example.techstore.Controllers.Admin.customers;

import com.example.techstore.Database.DatabaseConnection;
import com.example.techstore.Models.Customer;
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

public class CustomersController {

    @FXML
    private ListView<Customer> customersListView;
    @FXML
    private Button addButton;
    @FXML
    private TextField searchField;

    private ObservableList<Customer> customersList;
    private FilteredList<Customer> filteredData;

    @FXML
    public void initialize() {
        customersList = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(customersList, p -> true);

        customersListView.setItems(filteredData);
        loadCustomers();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(customer.getCustomerId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getContact().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void loadCustomers() {
        customersList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customer";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("name");
                String contact = resultSet.getString("contact");
                String address = resultSet.getString("address");

                Customer customer = new Customer(customerId, name, contact, address);
                customersList.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToAddCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/AddCustomer.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Customer");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Reload customers after adding new customer
            loadCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEditCustomer() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/EditCustomer.fxml"));
                Parent root = loader.load();

                EditCustomerController controller = loader.getController();
                controller.setCustomer(selectedCustomer);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Customer");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload customers after editing
                loadCustomers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToViewCustomer() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/ViewCustomer.fxml"));
                Parent root = loader.load();

                ViewCustomerController controller = loader.getController();
                controller.setCustomer(selectedCustomer);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("View Customer");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void switchToDeleteCustomer() {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/techstore/Views/DeleteCustomer.fxml"));
                Parent root = loader.load();

                DeleteCustomerController controller = loader.getController();
                controller.setCustomer(selectedCustomer);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Delete Customer");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Reload customers after deleting
                loadCustomers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
