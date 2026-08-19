package com.saucedemo.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.core.ProductDetails;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.testdata.TestData;

public class InventoryTests  extends BaseTest{
	
	@BeforeMethod
	public void login() {
		LoginPage loginPage = getPages().getLoginPage();
		loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);
	}
	
	
	@Test(description = "view Product list on Inventory page")
	public void viewProductsOnInventoryPage(){
		
		InventoryPage inventoryPage = getPages().getInventoryPage();
		Assert.assertEquals(inventoryPage.getPageTitleText(), "Products");
		Assert.assertTrue(inventoryPage.isCartIconVisible());
		
		List<String> actualProductNames = inventoryPage.getProductNamesInOrder();
		Assert.assertEquals(actualProductNames.size(), 6);
		Assert.assertEquals(actualProductNames,TestData.PRODUCT_NAMES);
		
	}
	
	@Test(description = "view any Product information")
	public void viewProductInfoOnInventoryPage(){
		
		InventoryPage inventoryPage = getPages().getInventoryPage();
		
		ProductDetails backpack = inventoryPage.getProductDetails(TestData.BACKPACK);
		Assert.assertEquals(backpack.getPrice(),"$29.99");
		Assert.assertTrue(backpack.getDescription().contains("carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection."));
		
		ProductDetails boltTshirt = inventoryPage.getProductDetails(TestData.BOLT);
		Assert.assertEquals(boltTshirt.getPrice(),"$15.99");
		Assert.assertTrue(boltTshirt.getDescription().contains("Get your testing superhero on with the Sauce Labs bolt T-shirt."));
		
	}
	
	@Test(description = "sort Products By name Z to A")
	public void sortProductsByZtoA(){
		
		InventoryPage inventoryPage = getPages().getInventoryPage();
		inventoryPage.sortByNameZA();
		 List<String> expectedOrder = new ArrayList<>(TestData.PRODUCT_NAMES);
		 Collections.reverse(expectedOrder);
		Assert.assertEquals(inventoryPage.getProductNamesInOrder(),expectedOrder);
	}
	
	@Test(description = "sort Products By Price Low to High",enabled=true)
	public void sortProductsByPriceLowToHigh(){
		
		InventoryPage inventoryPage = getPages().getInventoryPage();
		inventoryPage.sortByPriceLowToHigh();
		 List<Double> prices = inventoryPage.getProductPricesInOrder();
		 List<Double> sorted = prices.stream().sorted().collect(Collectors.toList());
		Assert.assertEquals(prices,sorted);
		Assert.assertEquals(prices.get(0),7.99,0.01);
		Assert.assertEquals(prices.get(prices.size()-1),49.99,0.01);
		
	}
	
	
	@Test(description = "sort Products By Price High to Low",enabled=true)
	public void sortProductsByPriceHighToLow(){
		
		InventoryPage inventoryPage = getPages().getInventoryPage();
		inventoryPage.sortByPriceHighToLow();
		 List<Double> prices = inventoryPage.getProductPricesInOrder();
		 List<Double> sortedDescendingOrder = prices.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
		Assert.assertEquals(prices,sortedDescendingOrder);
	}
	
	
	

}
