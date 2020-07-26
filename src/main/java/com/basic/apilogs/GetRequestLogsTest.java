package com.basic.apilogs;

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


public class GetRequestLogsTest {

    private String consumerKey = "djWeweudjkWEwejkwe";
    private String consumerSecret = "oe9eioieEweweekwli3323eejJKSIOIWOIWI23";
    private String accessToken = "aeweioerururiEWEWwe";
    private String accessSecret = "ewuwoieeWEEWEFWEFwfwfefwefwefwef";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://maps.googleapis.com";
        RestAssured.basePath = "/map/api";

    }

    @Test
    public void readTweetsTest() {
        given()
                .log() //add log
                //.headers()
                .ifValidationFails() //print log if it is failed
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("user_id", "username")
        .when()
                .get("/update.json")
        .then()
                .statusCode(200);

    }


}
