package com.saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.testdata.TestData;

public class CheckoutStepOneAndTwoTests extends BaseTest {

	private LoginPage loginPage;
	private CheckoutFlows bookingflow;
	private CheckoutPage checkoutPage;

	@BeforeMethod
	public void loginAndCheckOut() {
		loginPage = getPages().getLoginPage();
		loginPage.login(TestData.STANDARD_USER, TestData.PASSWORD);
		bookingflow = new CheckoutFlows(getPages());

		bookingflow.addProductToCart(TestData.BACKPACK);
		bookingflow.addProductToCart(TestData.BIKE_LIGHT);
		checkoutPage = getPages().getCheckoutPage();
	}

	/* Cart Step One Test cases */
	@Test(description = "required field validation - FirstName")
	public void firstNameErrorValidation() {
		bookingflow.goToCheckOutStepOne();
		getPages().getCheckoutPage().fillCheckoutInfo("", TestData.LAST_NAME, TestData.POSTAL_CODE);
		Assert.assertTrue(
				getPages().getCheckoutPage().getErrorMessage().contains(TestData.checkout_firstName_errorMessage));
	}

	/* Cart Step One Test cases */
	@Test(description = "required field validation - lastName")
	public void lastNameErrorValidation() {
		bookingflow.goToCheckOutStepOne();
		getPages().getCheckoutPage().fillCheckoutInfo(TestData.FIRST_NAME, "", TestData.POSTAL_CODE);
		Assert.assertTrue(
				getPages().getCheckoutPage().getErrorMessage().contains(TestData.checkout_lastName_errorMessage));
	}

	/* Cart Step One Test cases */
	@Test(description = "required field validation - postalCode")
	public void postalCodeErrorValidation() {
		bookingflow.goToCheckOutStepOne();
		getPages().getCheckoutPage().fillCheckoutInfo(TestData.FIRST_NAME, TestData.LAST_NAME, "");
		Assert.assertTrue(
				getPages().getCheckoutPage().getErrorMessage().contains(TestData.checkout_PostalCode_errorMessage));
	}

	/* Cart Step One Test cases */
	@Test(description = "complete Checkout Info")
	public void completeCheckoutInfo() {
		bookingflow.goToCheckOutStepOne();
		getPages().getCheckoutPage().fillCheckoutInfo(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.POSTAL_CODE);
		Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-two.html"));
	}

	/* Cart Step One Test cases */
	@Test(description = "complete Checkout Info")
	public void cancelCheckoutInfo() {
		bookingflow.goToCheckOutStepOne();
		getPages().getCheckoutPage().clickCancel();
		
		SoftAssert soft = new SoftAssert();
		
		soft.assertTrue(getDriver().getCurrentUrl().contains("cart.html"),"User on cart Page");
		soft.assertEquals(Integer.valueOf(getPages().getCartPage().getCartCountFromBadge()),Integer.valueOf(2),"Cart count should have 2");
		
		soft.assertAll();
	}
	
	
	/* Cart Step Two Test cases */
	@Test(description = "order overview Display Info")
	public void verifyOrderOverviewDisplay() {
		bookingflow.goToCheckOutStepTwo(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.POSTAL_CODE);
		
		SoftAssert soft = new SoftAssert();
		
		soft.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-two.html"),"User on cart step two Page");
		soft.assertEquals(Integer.valueOf(checkoutPage.getOverviewItems().size()),Integer.valueOf(2),"Cart count should have 2");
		soft.assertTrue(checkoutPage.getPaymentInfo().contains(TestData.PaymentInfo));
		soft.assertTrue(checkoutPage.getShippingInfo().contains(TestData.shippingInfo));
		soft.assertAll();
	}
	
	@Test(description = "verify Price & Tax calculation")
	public void verifyPriceAndTaxCalculation() {
		bookingflow.goToCheckOutStepTwo(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.POSTAL_CODE);
		
		double itemTotal = parseCurrency(checkoutPage.getItemTotalText());
		double taxTotal = parseCurrency(checkoutPage.getTaxText());
		double total = parseCurrency(checkoutPage.getTotalText());
		
		double expectedTax = itemTotal * 0.08;
		
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(itemTotal, 39.98,"Item total is incorrect");
		soft.assertEquals(taxTotal, 3.20,"Item total is incorrect");
		soft.assertEquals(total, 43.18,"Item total is incorrect");
		soft.assertEquals(Math.round(taxTotal), Math.round(expectedTax));
		soft.assertAll();
		
	}
	
	private double parseCurrency(String text) {
		String normalValue = text.replaceAll("[^0-9.]", "");
		return Double.parseDouble(normalValue);
	}


}
