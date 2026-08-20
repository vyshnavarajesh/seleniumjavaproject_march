package com.saucedemo.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.testdata.TestData;

public class CartManagementTests  extends BaseTest{
	
	private LoginPage loginPage;
	private InventoryPage inventoryPage;
	private CartPage cartPage;
	
	@BeforeMethod
	public void login() {
		loginPage = getPages().getLoginPage();
		loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);
		inventoryPage = getPages().getInventoryPage();
	}
	
	
	@Test(description = "Add Product Cart")
	public void addProductToCart(){
	
		Assert.assertEquals(inventoryPage.getPageTitleText(), "Products");
		inventoryPage.addProductToCart(TestData.BACKPACK);
		Assert.assertEquals(inventoryPage.getCartCount(), 1);
		
	}
	
	@Test(description = "Add & Remove product from  Cart in Inventory Page")
	public void addAndRemoveProductFromCartInInventoryPage(){
	
		Assert.assertEquals(inventoryPage.getPageTitleText(), "Products");
		inventoryPage.addProductToCart(TestData.BACKPACK);
		Assert.assertEquals(inventoryPage.getCartCount(), Integer.valueOf(1));
		inventoryPage.removeProductFromCart(TestData.BACKPACK);
		cartPage = getPages().getCartPage();
		Assert.assertNull(cartPage.getCartCountFromBadge());
		
	}
	
	@Test(description = "Add & Remove product from  Cart in CartPage")
	public void addAndRemoveProductFromCart(){
	
		Assert.assertEquals(inventoryPage.getPageTitleText(), "Products");
		inventoryPage.addProductToCart(TestData.BACKPACK);
		Assert.assertEquals(inventoryPage.getCartCount(), Integer.valueOf(1));
		inventoryPage.openCart();
		
		cartPage = getPages().getCartPage();
		cartPage.removeProductFromCart(TestData.BACKPACK);
	
		Assert.assertNull(cartPage.getCartCountFromBadge());
		
	}
	
	@Test(description = "Add Multiple Products to Cart")
	public void addMultipleProductsToCart(){
	
		List<String> productsToAdd = Arrays.asList(TestData.BACKPACK,
				TestData.BIKE_LIGHT,
				TestData.BOLT);
		
		for(String productName : productsToAdd) {
			inventoryPage.addProductToCart(productName);
		}
		
		org.testng.asserts.SoftAssert soft = new 	org.testng.asserts.SoftAssert();
		
		soft.assertEquals(inventoryPage.getCartCount(), Integer.valueOf(productsToAdd.size()),"cart count mismatch");
		
		inventoryPage.openCart();
		cartPage = getPages().getCartPage();
		soft.assertEquals(cartPage.getPageTitleText(),"Your Cart","cart page title mismatch");
		soft.assertEquals(cartPage.getCartItemCount(),Integer.valueOf(productsToAdd.size()),"cart count mismatch in cart Page");
		
		cartPage.proceedToCheckout();
		soft.assertEquals(cartPage.getPageTitleText(),"Checkout: Your Information","checkout stepone page title mismatch");
		soft.assertAll();
		
//		Assert.assertEquals(inventoryPage.getPageTitleText(), "Products");
//		inventoryPage.addProductToCart(TestData.BACKPACK);
//		Assert.assertEquals(inventoryPage.getCartCount(), 1);
//		
	}
	
	

}
