package com.basic.usefulfunction.responsetime;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;


//Note:
// given(): I have this information
// when(): I perform action
// then(): I need to check output


public class ResponseTimeCheckFailTest {

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
                //.log() //add log
                //.ifValidationFails() //print log if it is failed
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("user_id", "username")
        .when()
                .get("/user_timeline.json")
                //.time()
        .then()
                .time(lessThan(1L), TimeUnit.SECONDS); //verify and fail if response<1sec
                //.time(lessThan(800L), TimeUnit.MILLISECONDS); //verify and fail if response<1sec


    }


}
