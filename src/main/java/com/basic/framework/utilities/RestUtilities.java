package com.basic.framework.utilities;

import com.basic.framework.constants.Authentication;
import com.basic.framework.constants.Path;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class RestUtilities {
    public static String ENDPOINT;
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static RequestSpecification REQUEST_SPEC;
    public static ResponseSpecBuilder RESPONSE_BUILDER;
    public static ResponseSpecification RESPONSE_SPEC;

    public static void setEndPoint(String endPoint) {
        ENDPOINT = endPoint;
    }

    public static RequestSpecification getRequestSpecification() {
        AuthenticationScheme authenticationScheme =
                RestAssured.oauth(Authentication.CONSUME_KEY, Authentication.CONSUME_SECRET,
                        Authentication.ACCESS_TOKEN, Authentication.ACCESS_SECRET);

        REQUEST_BUILDER = new RequestSpecBuilder();
        REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
        REQUEST_BUILDER.setAuth(authenticationScheme);

        REQUEST_SPEC = REQUEST_BUILDER.build();

        return REQUEST_SPEC;
    }

    public static ResponseSpecification getResponseSpecification() {
        RESPONSE_BUILDER = new ResponseSpecBuilder();
        RESPONSE_BUILDER.expectStatusCode(200);
        RESPONSE_BUILDER.expectResponseTime(lessThan(3L), TimeUnit.SECONDS); //depend on expected response time

        RESPONSE_SPEC = RESPONSE_BUILDER.build();

        return RESPONSE_SPEC;
    }

    public static RequestSpecification createQueryParam(RequestSpecification requestSpec, String param, String value) {
        return requestSpec.queryParams(param, value);
    }

    public static RequestSpecification createQueryParam(RequestSpecification requestSpec, Map<String, String> mapQuery) {
        return requestSpec.queryParams(mapQuery);
    }

    public static RequestSpecification createPathParam(RequestSpecification requestSpec, String param, String value) {
        return requestSpec.pathParam(param, value);
    }

    public static Response getResponse() {
        return given().get(ENDPOINT);
    }

    public static Response getResponse(RequestSpecification reqSpec, String type) {
        REQUEST_SPEC.spec(reqSpec);
        Response response = null;

        if (type.equalsIgnoreCase("get")) {
            response = given().spec(REQUEST_SPEC).get(ENDPOINT);
        } if (type.equalsIgnoreCase("post")) {
            response = given().spec(REQUEST_SPEC).post(ENDPOINT);
        } if (type.equalsIgnoreCase("put")) {
            response = given().spec(REQUEST_SPEC).put(ENDPOINT);
        } if (type.equalsIgnoreCase("delete")) {
            response = given().spec(REQUEST_SPEC).delete(ENDPOINT);
        } else {
            System.out.println("That type is not supported");
        }

        response.then().log().all(); //show log
        response.then().spec(RESPONSE_SPEC); //verify all response
        return response;
    }

    public static JsonPath getJsonPath(Response response) {
        String pathString = response.asString();
        return new JsonPath(pathString);
    }

    public static XmlPath getXmlPath(Response response) {
        String pathString = response.asString();
        return new XmlPath(pathString);
    }

    public static void resetBasePath() {
        RestAssured.basePath = null;
    }

    public static void setContentType(String contentType) {
        given().contentType(contentType);
    }

}
