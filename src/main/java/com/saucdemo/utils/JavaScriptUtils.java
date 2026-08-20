package com.saucdemo.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JavaScriptUtils {
	
	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;
	
	public JavaScriptUtils(WebDriver driver)
	{
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
	}
	
	/*** 1. Refresh Page using JS ***/
	public void jsRefreshPage()
	{
		 js.executeScript("history.go(0)"); 
	}
	
	/*** 2. Navigate Back using JS ***/
	public void jsNavigateBack()
	{
		 js.executeScript("history.go(-1)"); 
	}
	
	/*** 3. Navigate Forward using JS ***/
	public void jsNavigateForward()
	{
		 js.executeScript("history.go(1)"); 
	}
	
	/*** 4. Get Current URL using JS ***/
	public String jsGetURL()
	{
		return js.executeScript("return document.URL;").toString(); 
	}
	
	/*** 5. Get Current Page title using JS ***/
	public String jsGetTitle()
	{
		return js.executeScript("return document.title;").toString(); 
	}
	
	/*** 6. Scroll page from top to Bottom using JS ***/
	public void jsScrollToptoBottom()
	{
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)"); 
	}
	
	/*** 6. Scroll page from bottom to Top using JS ***/
	public void jsScrollBottomtoTop()
	{
		js.executeScript("window.scrollTo(0,-document.body.scrollHeight)"); 
	}
	
	/*** 7. Scroll to specific element using  JS ***/
	public void jsScrollToElement(WebElement element)
	{
		js.executeScript("arguments[0].scrollIntoView(true);",element);
	}
	
	/*** 8. click on element using JS ***/
	public void jsElementClick(WebElement element)
	{
		js.executeScript("arguments[0].click();",element);
	}
	
	/*** 8. Highlight specific element using JS ***/
	public void jsHighlightElement(WebElement element)
	{
		js.executeScript("arguments[0].style.border='3px solid red'",element);
	}
	
	/*** 9. Enter value in specific element using JS ***/
    public void jsEnterValue(WebElement element, String value) {
        js.executeScript("arguments[0].value='" + value + "';", element);
    }

    /*** 10. Get value from specific element using JS ***/
    public String jsGetValue(WebElement element) {
        return js.executeScript("return arguments[0].value;", element).toString();
    }

    /*** 11.Zoom In/Out page using JS ***/
    public void jsZoomPage(String percentage) {
        js.executeScript("document.body.style.zoom='" + percentage + "'");
    }
    
    /*** 12. Vertical scroll from source to target using pixel ***/
    public void jsVerticalScrollByPixel(int sourcepixel, int targetpixel) {
        js.executeScript("window.scrollBy(" + sourcepixel + "," + targetpixel + ")");
    }
    
    /*** 13.Scroll to right (horizontal) ***/
    public void jsHorizontalScrollToRight()
	{
    	 js.executeScript("window.scrollTo(document.body.scrollWidth, 0)");
	}
   
    public void jsHorizontalscrollToRightByPixel(int pixelvalue) {
    	js.executeScript("window.scrollBy(" + pixelvalue + ", 0)");
    }
    
    public void jsHorizontalscrollToLefttByPixel(int pixelvalue) {
    	js.executeScript("window.scrollBy(-" + pixelvalue +", 0)");
    }
    
    public void jsHorizontalScrollToElement(WebElement element) {
    	js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth", element);
    }
    

}
