package com.saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.testdata.TestData;

public class LoginAuthenticationTests  extends BaseTest{
	
	@Test(description = "successful login with Standard user ")
	public void successfulLoginWithStandardUser(){
		
		LoginPage loginPage = getPages().getLoginPage();
		InventoryPage inventoryPage = getPages().getInventoryPage();
		
		loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory.html"));
		Assert.assertEquals(inventoryPage.getPageTitleText(), "Products");
		Assert.assertTrue(inventoryPage.isCartIconVisible());
	}
	
	@Test(description = "login failure with invalid userName ")
	public void loginFailureWithInvalidUserName(){
		
		LoginPage loginPage = getPages().getLoginPage();
	
		loginPage.login("invalid_user", TestData.PASSWORD);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("saucedemo.com"));
		Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface: Username and password do not match any user in this service"));
	}
	
	@Test(description = "login failure  with invalid password ")
	public void loginFailureWithInvalidPassword(){
		
		LoginPage loginPage = getPages().getLoginPage();
		
		loginPage.login(TestData.STANDARD_USER, TestData.INVALID_PASSSWORD);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("saucedemo.com"));
		Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface: Username and password do not match any user in this service"));
	}
	
	@Test(description = "login with locked out user ")
	public void loginFailureWithLockedUser(){
		
		
		LoginPage loginPage = getPages().getLoginPage();
		
		loginPage.login(TestData.LOCKED_USER, TestData.PASSWORD);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("saucedemo.com"));
		Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface: Sorry, this user has been locked out."));
	}
	
	@Test(description = "successful login with Error user ")
	public void successfulLoginWithErrorUser(){
		
		LoginPage loginPage = getPages().getLoginPage();
		InventoryPage inventoryPage = getPages().getInventoryPage();
		
		loginPage.login(TestData.ERROR_USER, TestData.PASSWORD);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory.html"));
		Assert.assertEquals(inventoryPage.getPageTitleText(), "Products");
		Assert.assertTrue(inventoryPage.isCartIconVisible());
	}
	
	

}
