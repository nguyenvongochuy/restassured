package com.basic.asserts;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


//Note:
// given(): I have this information
// when(): I perform action
// then(): I need to check output


public class HardAssertTest {

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
        .then()
                .statusCode(200)
                .log().body()
                .body("user.name", hasItem("RestAPI Automation"))
                .body("entities.hashtags[0].text", hasItem("multiple")) //there are 2 posted in twit and verify
                .body("entities.hashtags[0].size()", equalTo(2))
                .body("entities.hashtags[1].size()", lessThan(2));


    }


}
