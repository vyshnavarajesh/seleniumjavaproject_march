package com.saucedemo.testdata;

import java.util.Arrays;
import java.util.List;

public final class TestData {
    
    public static final String STANDARD_USER = "standard_user";
    public static final String LOCKED_USER = "locked_out_user";
    public static final String ERROR_USER = "error_user";
    public static final String PASSWORD = "secret_sauce";

    public static final String BACKPACK = "Sauce Labs Backpack";
    public static final String BIKE_LIGHT = "Sauce Labs Bike Light";
    public static final String BOLT = "Sauce Labs Bolt T-Shirt";
    

    public static final String  checkout_firstName_errorMessage = "Error: First Name is required";
    public static final String checkout_lastName_errorMessage = "Error: Last Name is required";
    public static final String  checkout_PostalCode_errorMessage = "Error: Postal Code is required";
    
    public static final String PaymentInfo = "SauceCard #31337";
    public static final String shippingInfo = "Free Pony Express Delivery!";


    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String POSTAL_CODE = "12345";
    
    public static final String INVALID_USERNAME = "invalid_user";
    public static final String INVALID_PASSSWORD = "invalid_password";
    
    public static final List<String> PRODUCT_NAMES = Arrays.asList(
            "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Fleece Jacket",
            "Sauce Labs Onesie",
            "Test.allTheThings() T-Shirt (Red)"
        );
}