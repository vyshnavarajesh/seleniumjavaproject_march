package com.saucedemo.tests;

import com.saucedemo.core.Config;
import com.saucedemo.core.DriverFactory;
import com.saucedemo.pages.PageManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    // ThreadLocal ensures each thread has its own driver and PageManager
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<PageManager> pageManagerThreadLocal = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    protected PageManager getPages() {
        return pageManagerThreadLocal.get();
    }

    @BeforeMethod
    public void setUp() {
    
        WebDriver driver = DriverFactory.createDriver();
        driver.get(Config.baseURL());

        driverThreadLocal.set(driver);
        pageManagerThreadLocal.set(new PageManager(driver));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
        }
        driverThreadLocal.remove();
        pageManagerThreadLocal.remove();
    }
}