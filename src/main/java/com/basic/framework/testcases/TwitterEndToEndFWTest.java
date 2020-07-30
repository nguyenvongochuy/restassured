package com.basic.framework.testcases;

import com.basic.framework.constants.EndPoints;
import com.basic.framework.constants.Path;
import com.basic.framework.utilities.RestUtilities;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.ws.Endpoint;

import static io.restassured.RestAssured.given;

public class TwitterEndToEndFWTest {

    private RequestSpecification reqSpec;
    private ResponseSpecification resSpec;
    private String tweetId;

    @BeforeClass
    public void setup() {
        reqSpec = RestUtilities.getRequestSpecification();
        reqSpec.basePath(Path.STATUSES);

        resSpec= RestUtilities.getResponseSpecification();

    }

    @Test
    public void postTweetsTest() {
        /*Response response = given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("status", "first tweet contain")
        .when()
                .post("/update.json")
        .then()
                .statusCode(200)
                .extract()
                .response();*/

        //reqSpec.queryParam("status", "first tweet contain");

        RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_POST);
        Response response = RestUtilities.getResponse(
                RestUtilities.createQueryParam(reqSpec,"status", "first tweet contain"), "post");

        /*tweetId = response.path("id_str");*/
        JsonPath jsonPath = RestUtilities.getJsonPath(response);
        tweetId = jsonPath.get("id_str");

        System.out.println("The response path id=" + tweetId);

    }

    @Test(dependsOnMethods = "postTweetsTest")
    public void readTweetsTest() {
        /*Response response = given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .queryParam("id", tweetId)
        .when()
                .get("/show.json")
        .then()
                .statusCode(200)
                .extract()
                .response();*/

        //reqSpec.queryParam("id", tweetId);

        RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_READ_SINGLE);
        Response response = RestUtilities.getResponse(
                RestUtilities.createQueryParam(reqSpec,"id", tweetId), "get");

        /*String text = response.path("text");*/
        JsonPath jsonPath = RestUtilities.getJsonPath(response);
        String text = jsonPath.get("text");
        System.out.println("The twitter text is:" + text);

    }

    @Test(dependsOnMethods = "readTweetsTest")
    public void deleteTweetsTest() {
        /*given()
                .auth()
                .oauth(consumerKey, consumerSecret, accessToken, accessSecret)
                .pathParam("id", tweetId)
        .when()
                .get("/destroy/{id}.json")
        .then()
                .statusCode(200);*/


/*        reqSpec.pathParam("id", tweetId);
        RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_DESTROY);
        Response response = RestUtilities.getResponse(
                RestUtilities.createPathParam(reqSpec,"id", tweetId), "post");*/

        //or can write
        given()
                .spec(RestUtilities.createPathParam(reqSpec,"id", tweetId))
        .when()
                .post(EndPoints.STATUSES_TWEET_DESTROY)
        .then()
                .spec(resSpec);

    }

}
