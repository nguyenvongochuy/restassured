package com.basic.bestpractice.ergast_com;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetCallBDD {

	private static final String BASE_URL = "http://ergast.com/api/f1/2017/circuits.json";
	
	@Test
	public void test_numberOfCircuits() {
		
		given().
		
		when()
			.get(BASE_URL).
		
		then()
			.statusCode(200)
			.assertThat()
			.contentType(ContentType.JSON).and()
			.body("MRData.CircuitTable.Circuits", hasSize(20));
		
	}
	
}
