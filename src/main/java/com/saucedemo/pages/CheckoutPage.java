package com.saucedemo.pages;

import com.saucedemo.core.ProductDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CheckoutPage extends BasePage {

    /* Locators – Step One (Information Form) */

    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");


    /* Locators – Step Two (Overview) */

    private final By itemTotalLabel = By.cssSelector(".summary_subtotal_label");
    private final By taxLabel = By.cssSelector(".summary_tax_label");
    private final By totalLabel = By.cssSelector(".summary_total_label");
    private final By overviewItems = By.cssSelector(".cart_item");
    private final By paymentInfoValue = By.cssSelector("[data-test='payment-info-value']");
    private final By shippingInfoValue = By.cssSelector("[data-test='shipping-info-value']");
    private final By finishButton = By.id("finish");
    private final By pageTitle = By.cssSelector(".title");

    /* Locators – Completion Page (Step Three) */

    private final By thankYouHeader = By.cssSelector(".complete-header");
    private final By dispatchText = By.cssSelector(".complete-text");
    private final By ponyImage = By.cssSelector(".pony_express");
    private final By backHomeButton = By.id("back-to-products");
    private final By pdfButton = By.xpath("//button[contains(translate(., 'PDF', 'pdf'), 'pdf')]");


    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /* Step One – Form Actions */
    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        visible(firstNameInput).sendKeys(firstName);
        visible(lastNameInput).sendKeys(lastName);
        visible(postalCodeInput).sendKeys(postalCode);
        clickable(continueButton).click();
    }

    public void clickContinue() {
        clickable(continueButton).click();
    }

    public void clickCancel() {
        clickable(cancelButton).click();
    }

    public String getErrorMessage() {
        return driver.findElements(errorMessage).isEmpty()
                ? null
                : visible(errorMessage).getText().trim();
    }

    /* Step Two – Overview Getters */
      public List<WebElement> getOverviewItems() {
        return allVisible(overviewItems);
    }

    public String getItemTotalText() {
        return visible(itemTotalLabel).getText().trim();
    }

    public String getPaymentInfo() {
        return visible(paymentInfoValue).getText().trim();
    }

    public String getShippingInfo() {
        return visible(shippingInfoValue).getText().trim();
    }

    public String getTaxText() {
        return visible(taxLabel).getText().trim();
    }

    public String getTotalText() {
        return visible(totalLabel).getText().trim();
    }

    public void clickFinish() {
        clickable(finishButton).click();
    }

    public ProductDetails getOverviewProductDetails(String productName) {
        WebElement product = allVisible(overviewItems).stream()
                .filter(item -> item.findElement(By.cssSelector(".inventory_item_name"))
                        .getText().trim().equals(productName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Overview product not found: " + productName));

        String name = product.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
        String description = product.findElement(By.cssSelector(".inventory_item_desc")).getText().trim();
        String price = product.findElement(By.cssSelector(".inventory_item_price")).getText().trim();
        return new ProductDetails(name, description, price);
    }

    /* Step Three – Completion Page Methods */
    public String getPageTitleText() {
        return visible(pageTitle).getText().trim();
    }

    public String getThankYouMessage() {
        return visible(thankYouHeader).getText().trim();
    }

    public String getDispatchMessage() {
        return visible(dispatchText).getText().trim();
    }

    public boolean isPonyImageVisible() {
        return visible(ponyImage).isDisplayed();
    }

    public boolean isBackHomeVisible() {
        return visible(backHomeButton).isDisplayed();
    }

    public void clickBackHome() {
        clickable(backHomeButton).click();
    }

    public boolean isPdfButtonVisible() {
        return !driver.findElements(pdfButton).isEmpty() && driver.findElement(pdfButton).isDisplayed();
    }

    public void clickPdfButton() {
        if (isPdfButtonVisible()) {
            clickable(pdfButton).click();
        }
    }
}