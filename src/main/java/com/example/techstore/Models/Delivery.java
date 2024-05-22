package com.example.techstore.Models;

public class Delivery {
    private int deliveryId;
    private int productId;
    private String deliveryDate;
    private int quantity;

    public Delivery(int deliveryId, int productId, String deliveryDate, int quantity) {
        this.deliveryId = deliveryId;
        this.productId = productId;
        this.deliveryDate = deliveryDate;
        this.quantity = quantity;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Delivery ID: " + deliveryId + ", Product ID: " + productId + ", Date: " + deliveryDate + ", Quantity: " + quantity;
    }
}
