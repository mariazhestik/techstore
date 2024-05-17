package com.example.techstore.Models;

public class Delivery {
    private int deliveryId;
    private int productId;
    private String deliveryDate;
    private int quantity;
    private int employeeId;

    public Delivery(int deliveryId, int productId, String deliveryDate, int quantity, int employeeId) {
        this.deliveryId = deliveryId;
        this.productId = productId;
        this.deliveryDate = deliveryDate;
        this.quantity = quantity;
        this.employeeId = employeeId;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}

