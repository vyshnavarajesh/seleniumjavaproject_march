package com.saucedemo.pages;

import com.saucedemo.core.ProductDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {


    private final By cartItems = By.cssSelector(".cart_item");
    private final By checkoutButton = By.id("checkout");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By pageTitle = By.cssSelector(".title");


    public CartPage(WebDriver driver) {
        super(driver);
    }


    public ProductDetails getCartProductDetails(String productName) {
        WebElement product = findCartProduct(productName);
        String name = product.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
        String description = product.findElement(By.cssSelector(".inventory_item_desc")).getText().trim();
        String price = product.findElement(By.cssSelector(".inventory_item_price")).getText().trim();
        return new ProductDetails(name, description, price);
    }

    public void removeProductFromCart(String productName) {
        WebElement product = findCartProduct(productName);
        product.findElement(By.cssSelector("button.cart_button")).click();
    }

    public void proceedToCheckout() {
        clickable(checkoutButton).click();
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public String getPageTitleText() {
        return visible(pageTitle).getText().trim();
    }

    public Integer getCartCountFromBadge() {
        List<WebElement> badges = driver.findElements(cartBadge);
        return badges.isEmpty() ? null : Integer.parseInt(badges.get(0).getText().trim());
    }

    public List<String> getAllProductNamesInCart() {
        return allVisible(cartItems).stream()
                .map(item -> item.findElement(By.cssSelector(".inventory_item_name")).getText().trim())
                .collect(Collectors.toList());
    }

    private WebElement findCartProduct(String productName) {
        return allVisible(cartItems).stream()
                .filter(item -> item.findElement(By.cssSelector(".inventory_item_name"))
                        .getText().trim().equals(productName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cart product not found: " + productName));
    }
}