package com.saucedemo.core;

public final class Config {

		private Config() {
			
		}
		
		public static String baseURL() {
			return System.getProperty("base.url","https://www.saucedemo.com/");	
		}
		
		public static String browser() {
			return System.getProperty("browser","chrome").toLowerCase();	
		}
		
		public static boolean headless() {
			return Boolean.parseBoolean(System.getProperty("headless","false"));
		}
		
		
		
		
}
