package com.saucedemo.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private DriverFactory() {}

    public static WebDriver createDriver() {
        String browser = Config.browser().toLowerCase();
        return switch (browser) {
            case "chrome" -> createChromeDriver();
            case "edge" -> createEdgeDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        applyCommonOptions(options); 
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }

    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        applyCommonOptions(options); 
        WebDriver driver = new EdgeDriver(options);
        return driver;
    }

    // Generic method to apply common preferences and arguments
    private static <T> void applyCommonOptions(T options) {
        if (options instanceof ChromeOptions chromeOpts) {
            if (Config.headless()) {
                chromeOpts.addArguments("--headless=new", "--window-size=1920,1080");
            }
            chromeOpts.setExperimentalOption("prefs", getCommonPrefs());
            chromeOpts.addArguments(
                "--disable-features=PasswordLeakDetection,SafetyTip,AutofillServerCommunications",
                "--disable-notifications",
                "--disable-save-password-bubble",
                "--disable-infobars",
                "--password-store=basic"
            );
        } else if (options instanceof EdgeOptions edgeOpts) {
            if (Config.headless()) {
                edgeOpts.addArguments("--headless=new", "--window-size=1920,1080");
            }
            edgeOpts.setExperimentalOption("prefs", getCommonPrefs());
            edgeOpts.addArguments(
                "--disable-features=PasswordLeakDetection,SafetyTip,AutofillServerCommunications",
                "--disable-notifications",
                "--disable-save-password-bubble",
                "--disable-infobars",
                "--password-store=basic"
            );
        } else {
            throw new IllegalArgumentException("Unsupported options type");
        }
    }

    private static Map<String, Object> getCommonPrefs() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("safebrowsing.enabled", false);
        return prefs;
    }
}