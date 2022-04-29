package com.cydeo.day7;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PutAndPatchRequestDemo extends SpartanTestBase {

    @DisplayName("PUT request to one spartan for update with Map")
    @Test
    public void test1(){

        //just like POST request we have different options to send json body, we will go with map
        Map<String,Object> putRequestMap = new LinkedHashMap<>();
        putRequestMap.put("gender","Male");
        putRequestMap.put("name","Severus Snape");
        putRequestMap.put("phone",8877445596l);

        given().contentType(ContentType.JSON).log().all()
                .body(putRequestMap)
                .and()
                .pathParam("id", 110)
                .when().put("/api/spartans/{id}")
                .then()
                .statusCode(204);

        //send a GET request to id number and make sure fields are updated
        given().accept(ContentType.JSON).log().all()
                .and()
                .pathParam("id",110)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200);

    }


    @DisplayName("PATCH request to one spartan for update with Map")
    @Test
    public void test2(){

        //just like POST request we have different options to send json body, we will go with map
        Map<String,Object> putRequestMap = new LinkedHashMap<>();

        putRequestMap.put("name","Severus");


        given().contentType(ContentType.JSON).log().all()
                .body(putRequestMap)
                .and()
                .pathParam("id", 110)
                .when().patch("/api/spartans/{id}")
                .then()
                .statusCode(204);

        //send a GET request to id number and make sure fields are updated
        given().accept(ContentType.JSON).log().all()
                .and()
                .pathParam("id",110)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200);



    }

    @DisplayName("DELETE one spartan")
    @Test
    public void test3(){

        int idToDelete = 109;

        given().pathParam("id",idToDelete)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204);

        //we can send GET request to id number and verify status code is 404
        given().pathParam("id",idToDelete)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(404);

    }





}
