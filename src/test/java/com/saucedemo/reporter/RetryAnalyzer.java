package com.saucedemo.reporter;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{
	
	private int retry = 0;
	private int max_retry = 2;

	@Override
	public boolean retry(ITestResult result) {
		if(retry < max_retry) {
			retry++;
			return true;
		}
		return false;
	}

}
