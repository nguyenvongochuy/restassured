package com.basic.framework.testcases;

import com.basic.framework.constants.EndPoints;
import com.basic.framework.constants.Path;
import com.basic.framework.utilities.RestUtilities;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItem;

public class UserTimeLineTest {

    private RequestSpecification reqSpec;
    private ResponseSpecification resSpec;

    @BeforeClass
    public void setup() {
        reqSpec= RestUtilities.getRequestSpecification();
        reqSpec.queryParam("user_id", "yourusername");
        reqSpec.basePath(Path.STATUSES);

        resSpec= RestUtilities.getResponseSpecification();

    }

    @Test
    public void readTweet() {
        given()
                //.spec(reqSpec) //retrieve all tweet from your account
                .spec(RestUtilities.createQueryParam(reqSpec, "count", "1"))  //retrieve only latest tweet
        .when()
                .get(EndPoints.STATUSES_USER_TIMELINE)
        .then()
                .log().all()
                .spec(resSpec)
                .body("user.screen_name", hasItem("apiautomation")); //add more expected responses

    }

    //use framework utilities
    @Test
    public void readTweet2() {
        RestUtilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);
        Response response = RestUtilities.getResponse(
                RestUtilities.createQueryParam(reqSpec, "count", "1"), "get");

        List<String> screenNameList = response.path("user.screen_name");
        Assert.assertTrue(screenNameList.contains("apiautomation"));

    }

}
