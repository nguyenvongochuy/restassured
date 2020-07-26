package com.basic.extractresponse;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TwitterExtractResponseTest {
    private String consumerKey = "djWeweudjkWEwejkwe";
    private String consumerSecret = "oe9eioieEweweekwli3323eejJKSIOIWOIWI23";
    private String accessToken = "aeweioerururiEWEWwe";
    private String accessSecret = "ewuwoieeWEEWEFWEFwfwfefwefwefwef";


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";

    }

    @Test
    public void statusCodeTest() {
        Response response = given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("status", "My tweet something")
        .when()
                .post("/update.json")
        .then()
                .statusCode(200)
        .extract().response();

        //extract id
        String id = response.path("id_str");

        //way to to extract json
        String responseString = response.asString();
        JsonPath jsonPath = new JsonPath(responseString);
        String name = jsonPath.get("user.name");


    }

}
