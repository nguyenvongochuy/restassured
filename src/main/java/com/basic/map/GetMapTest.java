package com.basic.map;

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


public class GetMapTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://maps.googleapis.com";
        RestAssured.basePath = "/map/api";

    }

    @Test(enabled = false)
    public void statusCodeTest() {
        given()
                .param("units", "imperial")
                .param("origins", "Washington,DC")
                .param("destinations", "New+York+City,NY")
                .param("key", "abczaSyAFNxOzcDNEZ9")
        .when()
                .get("/distancematrix/json")
        .then()
                .statusCode(200);

    }

    @Test
    public void responseTest() {
        Response response = given()
                .param("units", "imperial")
                .param("origins", "Washington,DC")
                .param("destinations", "New+York+City,NY")
                .param("key", "abczaSyAFNxOzcDNEZ9")
        .when()
                .get("/distancematrix/json")
        .then()
                .statusCode(404)
                .extract()
                .response();

        System.out.println("Print response: " + response.body().prettyPrint());


    }

    @Test
    public void responseTest1() {
        given()
                // .param() is the generic and can imply for 3 params (depends on param we can use generic or explicitly)
                // .queryParam E.g. .../get?abc='123'
                // .pathParam() E.g. .../123
                // .formParam() E.g. for form submit
                .param("units", "imperial")
                .param("origins", "Washington,DC")
                .param("destinations", "New+York+City,NY")
                .param("key", "abczaSyAFNxOzcDNEZ9")
        .when()
                .get("/distancematrix/json")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON).and()
                .body("row[0].element[0].distance.text", equalTo("255 mi"));



    }

}
