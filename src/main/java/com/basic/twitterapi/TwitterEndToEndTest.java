package com.basic.twitterapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TwitterEndToEndTest {
    private String consumerKey = "djWeweudjkWEwejkwe";
    private String consumerSecret = "oe9eioieEweweekwli3323eejJKSIOIWOIWI23";
    private String accessToken = "aeweioerururiEWEWwe";
    private String accessSecret = "ewuwoieeWEEWEFWEFwfwfefwefwefwef";

    private String tweetId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";

    }

    @Test
    public void postTweetsTest() {
        Response response = given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("status", "first tweet contain")
        .when()
                .post("/update.json")
        .then()
                .statusCode(200)
                .extract()
                .response();

        tweetId = response.path("id_str");
        System.out.println("The response path id=" + tweetId);

    }

    @Test(dependsOnMethods = "postTweetsTest")
    public void readTweetsTest() {
        Response response = given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("id", tweetId)
        .when()
                .get("/show.json")
        .then()
                .statusCode(200)
                .extract()
                .response();

        String text = response.path("text");
        System.out.println("The twitter text is:" + text);

    }

    @Test(dependsOnMethods = "readTweetsTest")
    public void deleteTweetsTest() {
        given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .pathParam("id", tweetId)
        .when()
                .post("/destroy/{id}.json")
        .then()
                .statusCode(200);

    }

}
