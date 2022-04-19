package com.cydeo.day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpartanGetRequests {

    String url = "http://54.197.9.248:8000";

    /*
    Given Accept type is application/json
    When user send GET request to /api/spartans end point
    Then status code must be 200
    And Response content type must be application/json
     */
    @DisplayName("GET all spartans")
    @Test
    public void test1(){

        Response response = RestAssured.
                                given()
                                        .accept(ContentType.JSON)
                                .when()
                                        .get(url + "/api/spartans");

        // print the status code
        System.out.println("response.statusCode() = " + response.statusCode());
        // print the content type
        System.out.println("response.contentType() = " + response.contentType());

        //how to test API?
        // verify status code is 200
        Assertions.assertEquals(200, response.statusCode());

        // verify content type is application/json
        Assertions.assertEquals("application/json", response.contentType());

    }

    /*
    Given accept header is application/json
    When users send a get request to api/spartans/3
    Then status code must be 200
    And content type must be application/json
    And json body should contain 'Fidole'
     */
    @DisplayName("GET individual spartan")
    @Test
    public void test2(){

        Response response = RestAssured.given().accept(ContentType.JSON).when().get(url+"/api/spartans/3");

        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.contentType() = " + response.contentType());

        response.prettyPrint();

        //verify status code is 200
        Assertions.assertEquals(200, response.getStatusCode());
        //verify content type is application/json
        Assertions.assertEquals("application/json",response.contentType());

        // verify Fidole exist in json body
       Assertions.assertTrue(response.getBody().asString().contains("Fidole"));
    }


    /*
    Given no headers provided
    When Users send GET request to /api/hello
        Then response status code should be 200
        And Content type header should be “text/plain;charset=UTF-8”
        And header should contain date
        And Content-Length should be 17
        And body should be “Hello from Spartan"
     */
    @DisplayName("GET request to api/hello")
    @Test
    public void test3(){

        Response response = RestAssured.when().get(url+"/api/hello");

        response.prettyPrint();

        //verify status code
        Assertions.assertEquals(200, response.getStatusCode());

        //verify content type
        Assertions.assertEquals("text/plain;charset=UTF-8", response.getContentType());

        // verify Date header exists in Response headers
        //we use hasHEaderWithName method to verify header exists or not - it returns boolean
        Assertions.assertTrue(response.headers().hasHeaderWithName("Date"));

        //to get header value we use header() method which accepts header name as parameter and return value as string
        System.out.println("response.header(\"Content-Length\") = " + response.header("Content-Length"));
        System.out.println("response.header(\"Connection\") = " + response.header("Connection"));
        //verify Content-length is 17
        Assertions.assertEquals("17" ,response.header("Content-Length"));

        //verify body is "Hello from Sparta"
        Assertions.assertEquals("Hello from Sparta",response.body().asString() );
    }








}
