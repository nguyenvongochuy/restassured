package com.basic.postwithobject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


//Note:
// given(): I have this information
// when(): I perform action
// then(): I need to check output


public class PostMapWithObjectTest {

    PlaceAddModel placeAddModel;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://maps.googleapis.com";
        RestAssured.basePath = "/map/api";

    }

    @Test
    public void statusCodeTest() {
        Map<String, Double> location = new HashMap<>();
        location.put("lat", -33.8669710);
        location.put("lng", 151.1958750);

        List<String> types = new ArrayList<>();
        types.add("shoe_store");

        placeAddModel = new PlaceAddModel();
        placeAddModel.setLocation(location);
        placeAddModel.setTypes(types);
        placeAddModel.setAccuracy(50);
        placeAddModel.setName("Google Shoes!");
        placeAddModel.setPhoneNumber("(02) 9374 4000");
        placeAddModel.setWebsite("http://www.google.com.au");
        placeAddModel.setLanguage("en-AU");


        given()
                // .param() is the generic and can imply for 3 params (depends on param we can use generic or explicitly)
                // .queryParam E.g. .../get?abc='123'
                // .pathParam() E.g. .../123
                // .formParam() E.g. for form submit
                .queryParam("key", "abczaSyAFNxOzcDNEZ9")
                /*.body("{"
                        + "\"location\": {"
                        + "\"lat\": -33.8669710,"
                        + "\"lng\": 151.1958750"
                        + "},"
                        + "\"accuracy\": 50,"
                        + "\"name\": \"Google Shoes!\","
                        + "\"phone_number\": \"(02) 9374 4000\","
                        + "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","
                        + "\"types\": [\"shoe_store\"],"
                        + "\"website\": \"http://www.google.com.au\","
                        + "\"language\": \"en-AU\","
                        + "}")*/
                .body(placeAddModel) //Jackson or Gson automatically convert Object to Json format
        .when()
                .post("/place/add/json")
        .then()
                .statusCode(200).and()
                .contentType(ContentType.JSON).and()
                .body("scope", equalTo("APP")).and()
                .body("status", equalTo("OK"));

    }


}
