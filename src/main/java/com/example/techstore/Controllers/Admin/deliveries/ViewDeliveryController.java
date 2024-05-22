package com.example.techstore.Controllers.Admin.deliveries;

import com.example.techstore.Models.Delivery;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewDeliveryController {

    @FXML
    private Label deliveryIdLabel;
    @FXML
    private Label productIdLabel;
    @FXML
    private Label deliveryDateLabel;
    @FXML
    private Label quantityLabel;

    public void setDelivery(Delivery delivery) {
        deliveryIdLabel.setText(String.valueOf(delivery.getDeliveryId()));
        productIdLabel.setText(String.valueOf(delivery.getProductId()));
        deliveryDateLabel.setText(delivery.getDeliveryDate());
        quantityLabel.setText(String.valueOf(delivery.getQuantity()));
    }
}
