package com.saucedemo.pages;

import com.saucedemo.core.ProductDetails;
import com.saucedemo.testdata.TestData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class InventoryPage extends BasePage {

    // =============================================
    // Locators
    // =============================================
    private final By inventoryItems = By.cssSelector(".inventory_item");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");
    private final By pageTitle = By.cssSelector(".title");
    private final By sortDropdown = By.cssSelector(".product_sort_container");
    private final By itemName = By.cssSelector(".inventory_item_name");
    private final By itemPrice = By.cssSelector(".inventory_item_price");
    private final By burgerMenuButton = By.id("react-burger-menu-btn");
    private final By allItemsLink = By.id("inventory_sidebar_link");
    private final By aboutLink = By.id("about_sidebar_link");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By cartIcon = By.cssSelector(".shopping_cart_link");
    private final By productDetailContainer = By.cssSelector(".inventory_details_desc_container");
    private final By backToProductsButton = By.id("back-to-products");

    private static final Map<String, String> ADD_TO_CART_DATA_TEST = new HashMap<>();
    private static final Map<String, String> REMOVE_FROM_CART_DATA_TEST = new HashMap<>();

    static {
       
        List<String> productNames = TestData.PRODUCT_NAMES;

        List<String> addDataTestIds = Arrays.asList(
                "add-to-cart-sauce-labs-backpack",
                "add-to-cart-sauce-labs-bike-light",
                "add-to-cart-sauce-labs-bolt-t-shirt",
                "add-to-cart-sauce-labs-fleece-jacket",
                "add-to-cart-sauce-labs-onesie",
                "add-to-cart-test.allthethings()-t-shirt-(red)"
        );

        if (productNames.size() != addDataTestIds.size()) {
            throw new IllegalStateException("Product names and data-test IDs count mismatch");
        }

        for (int i = 0; i < productNames.size(); i++) {
            String name = productNames.get(i);
            String addId = addDataTestIds.get(i);
            ADD_TO_CART_DATA_TEST.put(name, addId);
            REMOVE_FROM_CART_DATA_TEST.put(name, addId.replace("add-to-cart-", "remove-"));
        }
    }

  
    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    private String getAddToCartDataTest(String productName) {
        String dataTest = ADD_TO_CART_DATA_TEST.get(productName);
        if (dataTest == null) {
            throw new IllegalArgumentException("No data-test mapping found for product: " + productName);
        }
        return dataTest;
    }

    private String getRemoveFromCartDataTest(String productName) {
        String dataTest = REMOVE_FROM_CART_DATA_TEST.get(productName);
        if (dataTest == null) {
            throw new IllegalArgumentException("No data-test mapping found for product: " + productName);
        }
        return dataTest;
    }

    /*
    public void addProductToCart(String productName) {
        String dataTestId = getAddToCartDataTest(productName);
        By buttonLocator = By.cssSelector("button[data-test='" + dataTestId + "']");
        driver.findElement(buttonLocator).click();
    }

    public void removeProductFromCart(String productName) {
        String dataTestId = getRemoveFromCartDataTest(productName);
        By buttonLocator = By.cssSelector("button[data-test='" + dataTestId + "']");
        driver.findElement(buttonLocator).click();
    }*/
    
    public void addProductToCart(String productName) {
        String dataTestId = getAddToCartDataTest(productName);
        By buttonLocator = By.cssSelector("button[data-test='" + dataTestId + "']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
        button.click();
        // Wait for cart badge to update (optional)
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
    }

    public void removeProductFromCart(String productName) {
        String dataTestId = getRemoveFromCartDataTest(productName);
        By buttonLocator = By.cssSelector("button[data-test='" + dataTestId + "']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
        button.click();
        // Wait for cart badge to disappear or update
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
    }

    public Integer getCartCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        return badges.isEmpty() ? null : Integer.parseInt(badges.get(0).getText().trim());
    }
    
    public void waitForInventoryPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryItems));
    }

    public void openCart() {
        clickable(cartLink).click();
    }

    public boolean isCartIconVisible() {
        return visible(cartIcon).isDisplayed();
    }


    public ProductDetails getProductDetails(String productName) {
        WebElement product = findProduct(productName);
        String name = product.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
        String description = product.findElement(By.cssSelector(".inventory_item_desc")).getText().trim();
        String price = product.findElement(By.cssSelector(".inventory_item_price")).getText().trim();
        return new ProductDetails(name, description, price);
    }

    public String getPageTitleText() {
        return visible(pageTitle).getText().trim();
    }

    public List<WebElement> getAllInventoryItems() {
        return allVisible(inventoryItems);
    }

    public List<String> getProductNamesInOrder() {
        return driver.findElements(itemName).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPricesInOrder() {
        List<Double> prices = new ArrayList<>();
        for (WebElement webElement : driver.findElements(itemPrice)) {
            prices.add(Double.parseDouble(webElement.getText().replace("$", "").trim()));
        }
        return prices;
    }

    public void sortByNameZA() {
        new Select(visible(sortDropdown)).selectByValue("za");
    }

    public void sortByPriceLowToHigh() {
        new Select(visible(sortDropdown)).selectByValue("lohi");
    }

    public void sortByPriceHighToLow() {
        new Select(visible(sortDropdown)).selectByValue("hilo");
    }

 
    public void clickProductImageByIndex(int index) {
        List<WebElement> images = driver.findElements(By.cssSelector(".inventory_item_img img"));
        if (index < 0 || index >= images.size()) {
            throw new IllegalArgumentException("Invalid product image index: " + index);
        }
        images.get(index).click();
    }

    public boolean isProductDetailVisible() {
        return visible(productDetailContainer).isDisplayed();
    }

    public boolean isBackButtonVisible() {
        return visible(backToProductsButton).isDisplayed();
    }

    public void clickBackToProducts() {
        clickable(backToProductsButton).click();
    }

    public void openMenu() {
        clickable(burgerMenuButton).click();
    }

    public void clickAllItemsFromMenu() {
        clickable(allItemsLink).click();
    }

    public String getAboutLinkHref() {
        return visible(aboutLink).getAttribute("href");
    }

    public boolean isAboutLinkVisible() {
        return visible(aboutLink).isDisplayed();
    }

    public void logoutFromMenu() {
        clickable(logoutLink).click();
    }

  
    private WebElement findProduct(String productName) {
        List<WebElement> products = driver.findElements(inventoryItems);
        return products.stream()
                .filter(item -> item.findElement(By.cssSelector(".inventory_item_name"))
                        .getText().trim().equals(productName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Product not found: " + productName));
    }
}