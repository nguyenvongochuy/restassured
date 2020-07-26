package com.basic.spec;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThan;


//Note:
// given(): I have this information
// when(): I perform action
// then(): I need to check output

//USE Spec (request or response for common) to config authen, URI, path, userlogin, common response...

public class ResponseSpecificationTest {

    private String consumerKey = "djWeweudjkWEwejkwe";
    private String consumerSecret = "oe9eioieEweweekwli3323eejJKSIOIWOIWI23";
    private String accessToken = "aeweioerururiEWEWwe";
    private String accessSecret = "ewuwoieeWEEWEFWEFwfwfefwefwefwef";

    AuthenticationScheme authenticationScheme;

    RequestSpecBuilder requestBuilder;
    static RequestSpecification requestSpec;

    ResponseSpecBuilder responseBuilder;
    static ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {
        //RestAssured.baseURI = "https://maps.googleapis.com";
        //RestAssured.basePath = "/map/api";

        //Request spec for COMMON
        authenticationScheme =
                RestAssured.oauth(consumerKey, consumerSecret, accessToken, accessSecret);
        requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://maps.googleapis.com");
        requestBuilder.setBasePath("/map/api");
        requestBuilder.addQueryParam("user_id", "username");
        requestBuilder.setAuth(authenticationScheme);
        requestSpec = requestBuilder.build();

        //response spec for COMMON
        responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectStatusCode(200);
        responseBuilder.expectBody("user.name", hasItem("RestAPI automation"));
        responseSpec = responseBuilder.build();
    }

    @Test
    public void readTweetsTest() {
        given()
                //.auth()
                //.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                //.queryParam("user_id", "username")
                .spec(requestSpec)
        .when()
                .get("/user_timeline.json")
                //.time()
        .then()
                //.statusCode(200)
                //.body("user.name", hasItem("RestAPI automation"));
                .spec(responseSpec) //use response Spec for common
                .body("user.screen_name", hasItem("RestAPI automation")) //use more custom response validation
                .time(lessThan(1L), TimeUnit.SECONDS); //use more custom response validation
    }


}
