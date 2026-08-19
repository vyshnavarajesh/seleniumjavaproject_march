package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{
	
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");
    private final By errorCloseButton = By.cssSelector("button[data-test='error-button']");
    private final By titleText = By.cssSelector(".title");
 

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	
	public void login(String username, String password) {
		visible(usernameInput).sendKeys(username);
		visible(passwordInput).sendKeys(password);
		clickable(loginButton).click();
		
	}

	public void enterUserName(String username) {
		visible(usernameInput).sendKeys(username);		
	}
	
	public void enterPassword(String password) {
		visible(passwordInput).sendKeys(password);		
	}
	
	public void clickLogin() {
		clickable(loginButton).click();		
	}
	
	
	public String getErrorMessage() {
		if(driver.findElements(errorMessage).isEmpty()) {
			return null;
		}
		return visible(errorMessage).getText().trim();

	}
	
	public boolean isUserNameFieldDisplayed() {
		return visible(usernameInput).isDisplayed();
	}
	
	public boolean isPasswordFieldDisplayed() {
		return visible(passwordInput).isDisplayed();
	}
	
	
	public String getPageTitleText() {
		if(driver.findElements(titleText).isEmpty()) {
			return null;
		}
		return visible(titleText).getText().trim();
	}
	
}
