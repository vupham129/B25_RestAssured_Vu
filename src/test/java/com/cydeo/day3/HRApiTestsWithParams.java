package com.cydeo.day3;

import com.cydeo.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class HRApiTestsWithParams extends HRTestBase {

    @Test
    public void test1(){
        Response response = get("/regions");
        response.prettyPrint();
    }


    /*
        Given accept type is Json
        And parameters: q = {"region_id":2}
        When users sends a GET request to "/countries"
        Then status code is 200
        And Content type is application/json
        And Payload should contain "United States of America"
     */

    @DisplayName("GET request /countries with query params")
    @Test
    public void test2(){

        Response response = given()
                .log().all().accept(ContentType.JSON)
                .queryParam( "q", "{\"region_id\":2}")
                .when().get("/countries");

        //verify status code
        assertEquals(200, response.statusCode());

        //verify content type
        assertEquals("application/json",response.contentType());

        //verify "United States of America"
        assertEquals(true, response.body().asString().contains("United States of America"));

    }

    /*
        HW
        Send a GET request to employees and get only employees who works as a IT_PROG

         */

    @DisplayName("Get request to employees and get only employees who works as a IT_PROG")
    @Test
    public void test3(){

        Response response = given().queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        response.prettyPrint();


    }





    /*
        send a GET request o employees endpoint, filter only Job_id IT_PROG
        then assert that all job_ids are IT_PROG
     */




}
