package com.saucedemo.reporter;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.saucedemo.tests.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.saucdemo.utils.ScreenshotUtil;

public class TestListener implements ITestListener {

	ExtentReports extent = ExtentManager.getInstance();
	//ExtentTest test; // Non local Thread Implementation
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		// test = extent.createTest(result.getMethod().getMethodName());
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
	
		extentTest.get().fail(result.getThrowable());
		 WebDriver driver = BaseTest.getDriver();
		if (driver != null) {
			String path = ScreenshotUtil.capture(driver, result.getMethod().getMethodName());
			extentTest.get().addScreenCaptureFromPath(path);
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		extentTest.get().skip("Test Skipped");
	}

	@Override
	public void onFinish(org.testng.ITestContext context) {
		extent.flush();
	}
}