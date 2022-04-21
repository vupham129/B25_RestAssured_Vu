package com.cydeo.day3;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanTestsWithParameters extends SpartanTestBase {

      /*  Given accept type is Json
          And Id parameter value is 5
          When user sends GET request to /api/spartans/{id}
          Then response status code should be 200
          And response content-type: application/json
          And "Blythe" should be in response payload
       */

    @DisplayName("GET request to /api/spartans/{id} with ID 5")
    @Test
    public void test1(){
        Response response =  given().accept(ContentType.JSON).and().pathParam("id",5)
                            .when().get("/api/spartans/{id}");

        //verify status code
        assertEquals(200, response.statusCode());

        //verify content type
        assertEquals("application/json",response.contentType());

        //verify "Blythe" inside the payload
        assertTrue(response.body().asString().contains("Blythe"));



    }







}
