package com.example.techstore.Models;

public class StoreInventory {
    private int storeInventoryId;
    private int productId;
    private int quantity;

    public StoreInventory(int storeInventoryId, int productId, int quantity) {
        this.storeInventoryId = storeInventoryId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getStoreInventoryId() {
        return storeInventoryId;
    }

    public void setStoreInventoryId(int storeInventoryId) {
        this.storeInventoryId = storeInventoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
