package com.example.techstore.Controllers.Admin.brands;

import com.example.techstore.Models.Brand;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewBrandController {

    @FXML
    private Label brandIdLabel;
    @FXML
    private Label nameLabel;

    public void setBrand(Brand brand) {
        brandIdLabel.setText(String.valueOf(brand.getBrandId()));
        nameLabel.setText(brand.getName());
    }
}
