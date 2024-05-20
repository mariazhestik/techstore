package com.example.techstore.Controllers.Admin.products;

import com.example.techstore.Models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewProductController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label brandIdLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label processorLabel;
    @FXML
    private Label memoryLabel;
    @FXML
    private Label ramLabel;
    @FXML
    private Label screenTypeLabel;
    @FXML
    private Label materialOfCorpsLabel;
    @FXML
    private Label colourLabel;
    @FXML
    private Label dimensionLabel;
    @FXML
    private Label batteryLabel;
    @FXML
    private Label statusLabel;

    public void setProduct(Product product) {
        nameLabel.setText(product.getName());
        brandIdLabel.setText(String.valueOf(product.getBrandId()));
        priceLabel.setText(String.valueOf(product.getPrice()));
        processorLabel.setText(product.getProcessor());
        memoryLabel.setText(product.getMemory());
        ramLabel.setText(product.getRam());
        screenTypeLabel.setText(product.getScreenType());
        materialOfCorpsLabel.setText(product.getMaterialOfCorps());
        colourLabel.setText(product.getColour());
        dimensionLabel.setText(product.getDimension());
        batteryLabel.setText(product.getBattery());
        statusLabel.setText(product.getStatus());
    }
}
