package com.basic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


//Note:
// given(): I have this information
// when(): I perform action
// then(): I need to check output


public class PostMapTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://maps.googleapis.com";
        RestAssured.basePath = "/map/api";

    }

    @Test
    public void statusCodeTest() {
        given()
                .param("key", "abczaSyAFNxOzcDNEZ9")
                .body("{"
                        + "\"location\": {"
                        + "\"lat\": -33.8669710,"
                        + "\"lng\": 151.1958750"
                        + "},"
                        + "\"accuracy\": 50,"
                        + "\"name\": \"Google Shoes!\","
                        + "\"phone_number\": \"(02) 9374 4000\","
                        + "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","
                        + "\"types\": [\"shoe_store\"],"
                        + "\"website\": \"http://www.google.com.au\","
                        + "\"language\": \"en-AU\","
                        + "}")
        .when()
                .post("/place/add/json")
        .then()
                .statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .body("scope", equalTo("APP")).and()
                .body("status", equalTo("OK"));

    }


}
