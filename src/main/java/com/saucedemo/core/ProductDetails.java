package com.saucedemo.core;


import java.util.Objects;

public class ProductDetails {
    private final String name;
    private final String description;
    private final String price;

    public ProductDetails(String name, String description, String price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public double getPriceValue() {
        return Double.parseDouble(price.replace("$", "").trim());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ProductDetails that = (ProductDetails) object;
        return Objects.equals(name, that.name)
            && Objects.equals(description, that.description)
            && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price);
    }
}