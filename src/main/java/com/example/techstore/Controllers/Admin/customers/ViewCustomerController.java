package com.example.techstore.Controllers.Admin.customers;

import com.example.techstore.Models.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewCustomerController {

    @FXML
    private Label customerIdLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label contactLabel;
    @FXML
    private Label addressLabel;

    public void setCustomer(Customer customer) {
        customerIdLabel.setText(String.valueOf(customer.getCustomerId()));
        nameLabel.setText(customer.getName());
        contactLabel.setText(customer.getContact());
        addressLabel.setText(customer.getAddress());
    }
}
