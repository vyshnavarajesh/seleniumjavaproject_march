package com.saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.testdata.TestData;

public class CheckOutCompletionTests extends BaseTest{
	
	private LoginPage loginPage;
	private CheckoutFlows bookingflow;
	private CheckoutPage checkoutPage;

	@BeforeMethod
	public void completePurchaseFlow() {
		
		loginPage = getPages().getLoginPage();
		loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);
		bookingflow = new CheckoutFlows(getPages());

		bookingflow.addProductToCart(TestData.BACKPACK);
		bookingflow.addProductToCart(TestData.BIKE_LIGHT);
		bookingflow.completeCheckout(TestData.FIRST_NAME,TestData.LAST_NAME, TestData.POSTAL_CODE);
		checkoutPage = getPages().getCheckoutPage();
	
	}
	
	@Test(description = "Verify order completion")
	public void verifyOrderCompletion()
	{
		SoftAssert soft = new SoftAssert();
		
		soft.assertEquals(checkoutPage.getPageTitleText(),"Checkout: Complete!", "Page title mismatch");
		soft.assertEquals(checkoutPage.getThankYouMessage().toLowerCase().trim(), "thank you for your order!","Thank you message missing");
		soft.assertTrue(checkoutPage.getThankYouMessage().toLowerCase().trim().contains("thank you"));
		soft.assertTrue(checkoutPage.getDispatchMessage().toLowerCase().trim().contains("dispatched"));
		soft.assertTrue(checkoutPage.isBackHomeVisible(),"BackHome Button not visible");
		soft.assertTrue(checkoutPage.isPdfButtonVisible(),"PDF Button is missing");
		checkoutPage.clickPdfButton();
		soft.assertAll();
	}
	
	@Test(description = "Verify navigate back from completion to inventory Page",enabled=true)
	public void verifyNavigateBackToHome()
	{
		
		checkoutPage.clickBackHome();
		SoftAssert soft = new SoftAssert();
		
		soft.assertTrue(getDriver().getCurrentUrl().contains("inventory.html"));
		soft.assertEquals(getPages().getInventoryPage().getPageTitleText(), "Products");
		soft.assertNull(getPages().getInventoryPage().getCartCount());
		soft.assertAll();
	}


}
