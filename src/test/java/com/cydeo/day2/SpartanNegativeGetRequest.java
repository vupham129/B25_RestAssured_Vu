package com.cydeo.day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanNegativeGetRequest {
    //beforeall is the same thing with beforeClass in testng
    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://54.197.9.248:8000" ;
    }

    /*
    Given Accept type is application/json
    When user send GET request to /api/spartans end point
    Then status code must be 200
    And Response content type must be application/json
     */
    @DisplayName("GET all spartans")
    @Test
    public void test1(){

        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get( "/api/spartans");

        // print the status code
        System.out.println("response.statusCode() = " + response.statusCode());
        // print the content type
        System.out.println("response.contentType() = " + response.contentType());

        //how to test API?
        // verify status code is 200
        assertEquals(200, response.statusCode());

        // verify content type is application/json
        assertEquals("application/json", response.contentType());

    }

    /*
    TASK:
    Given Accept type application/xml
    When user send GET request to /api/spartans/10 end point
    Then status code must be 406
    And response Content Type must be application/xml;charset=UTF-8;
     */

    @DisplayName("GET request to /api/spartans/10")
    @Test
    public void test2(){

        Response response =given().accept(ContentType.XML).when().get("/api/spartans/10") ;

        // verify status code 406
        assertEquals(406, response.statusCode());

        // verify content type must be
        assertEquals("application/xml;charset=UTF-8", response.contentType());

    }


}
