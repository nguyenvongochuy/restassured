package com.basic.spec;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;


//Note:
// given(): I have this information
// when(): I perform action
// then(): I need to check output

//USE Spec (request or response for common) to config authen, URI, path, userlogin, common response...

public class RequestSpecificationTest {

    private String consumerKey = "djWeweudjkWEwejkwe";
    private String consumerSecret = "oe9eioieEweweekwli3323eejJKSIOIWOIWI23";
    private String accessToken = "aeweioerururiEWEWwe";
    private String accessSecret = "ewuwoieeWEEWEFWEFwfwfefwefwefwef";

    private AuthenticationScheme authenticationScheme;

    RequestSpecBuilder requestBuilder;
    static RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        //RestAssured.baseURI = "https://maps.googleapis.com";
        //RestAssured.basePath = "/map/api";

        //request spec for COMMON
        authenticationScheme =
                RestAssured.oauth(consumerKey, consumerSecret, accessToken, accessSecret);
        requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("https://maps.googleapis.com");
        requestBuilder.setBasePath("/map/api");
        requestBuilder.addQueryParam("user_id", "username");
        requestBuilder.setAuth(authenticationScheme);

        requestSpec = requestBuilder.build();

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
                .statusCode(200);


    }


}
