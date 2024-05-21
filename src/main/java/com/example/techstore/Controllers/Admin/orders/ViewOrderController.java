package com.example.techstore.Controllers.Admin.orders;

import com.example.techstore.Models.Orders;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewOrderController {

    @FXML
    private Label orderIdLabel;
    @FXML
    private Label customerIdLabel;
    @FXML
    private Label employeeIdLabel;
    @FXML
    private Label productIdLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label statusLabel;

    public void setOrder(Orders order) {
        orderIdLabel.setText(String.valueOf(order.getOrderId()));
        customerIdLabel.setText(String.valueOf(order.getCustomerId()));
        employeeIdLabel.setText(String.valueOf(order.getEmployeeId()));
        productIdLabel.setText(String.valueOf(order.getProductId()));
        dateLabel.setText(order.getDate());
        totalAmountLabel.setText(String.valueOf(order.getTotalAmount()));
        statusLabel.setText(order.getStatus());
    }
}
