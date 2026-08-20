package com.saucedemo.tests;

import org.openqa.selenium.WebDriver;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.PageManager;
import com.saucedemo.testdata.TestData;

public class CheckoutFlows {
	
	private final PageManager pages;
	
	public CheckoutFlows(PageManager pages) {
		this.pages = pages;
	}
	
	 public InventoryPage inventory() {
	        return pages.getInventoryPage();
	    }
	 
	 public CartPage cart() {
	        return pages.getCartPage();
	    }

	 public CheckoutPage checkout() {
	        return pages.getCheckoutPage();
	    }

	 public void goToCart() {
		 inventory().openCart();
	 }
	 
	 public void goToCheckOutStepOne() {
		 goToCart() ; // this will take user to Cart
		 cart().proceedToCheckout();
	 }
	 
	 public void goToCheckOutStepTwo(String firstName, String lastName, String postalCode) {
		 goToCheckOutStepOne() ;
		 checkout().fillCheckoutInfo(firstName, lastName, postalCode);
	 }

	 public void completeCheckout(String firstName, String lastName, String postalCode) {
		 goToCheckOutStepTwo(firstName, lastName, postalCode);
		 checkout().clickFinish();
	 }

	 public void addProductToCart(String productName) {
			inventory().addProductToCart(productName);
	 }

}
