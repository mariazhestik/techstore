package com.example.techstore.Models;

public class Product {
    private int productId;
    private int brandId;
    private String name;
    private double price;
    private String processor;
    private String memory;
    private String ram;
    private String screenType;
    private String materialOfCorps;
    private String colour;
    private String dimension;
    private String battery;
    private String status;

    public Product(int productId, int brandId, String name, double price, String processor, String memory, String ram, String screenType, String materialOfCorps, String colour, String dimension, String battery, String status) {
        this.productId = productId;
        this.brandId = brandId;
        this.name = name;
        this.price = price;
        this.processor = processor;
        this.memory = memory;
        this.ram = ram;
        this.screenType = screenType;
        this.materialOfCorps = materialOfCorps;
        this.colour = colour;
        this.dimension = dimension;
        this.battery = battery;
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getMaterialOfCorps() {
        return materialOfCorps;
    }

    public void setMaterialOfCorps(String materialOfCorps) {
        this.materialOfCorps = materialOfCorps;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " (" + status + ")";
    }
}
