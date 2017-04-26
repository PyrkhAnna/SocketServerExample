package com.epam.ws1;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class testGetMethod {
	@BeforeClass
	public void setUpRestAssured() {
		String port = System.getenv("server.port");
		if (port == null) {
			RestAssured.port = Integer.valueOf(8080);
		} else {
			RestAssured.port = Integer.valueOf(port);
		}
		String basePath = System.getProperty("server.base");
		if (basePath==null) {
			basePath = "/book/";
		}
		RestAssured.basePath= basePath;
		String baseHost = System.getProperty("server.host");
		if (baseHost==null) {
			baseHost = "http://localhost";
		}
		RestAssured.baseURI = baseHost;
	}
	@Test
	public void testGetMethod() {
	//	Response rpBook = given().when().get("/book").thenReturn();
	}
	
}
