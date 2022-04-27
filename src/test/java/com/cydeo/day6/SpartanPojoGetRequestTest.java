package com.cydeo.day6;

import com.cydeo.pojo.Search;
import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class SpartanPojoGetRequestTest extends SpartanTestBase{

    @DisplayName("GET one spartan and convert it to Spartan Object")
    @Test
    public void test1(){

       Response response = given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id",15)
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .extract().response();

       //Deserialize --> JSON to POJO (Java custom class)
        //2 different way to do this
        //1. using as() method
        // we convert json response to spartan object with the help of jackspm
        Spartan spartan15 = response.as(Spartan.class);

        System.out.println(spartan15);

        System.out.println("spartan15.getName() = " + spartan15.getName());
        System.out.println("spartan15.getPhone() = " + spartan15.getPhone());
        System.out.println("spartan15.getGender() = " + spartan15.getGender());

        //second way of deserialize to java
        //2. using JsonPath to deserialize to custome class
        JsonPath jsonPath = response.jsonPath();

        Spartan s15 = jsonPath.getObject("",Spartan.class);
        System.out.println("s15.getName() = " + s15.getName());
        System.out.println("s15.getGender() = " + s15.getGender());


    }

    @DisplayName("GET one spartan from search endpoint and use POJO")
    @Test
    public void test2(){

       JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/spartans/search")
                .then()
                .statusCode(200)
                .extract().response().jsonPath();

        //get the second spartan from content list and put inside the spartan object
        Spartan spartan = jsonPath.getObject("content[1]",Spartan.class);
        System.out.println("spartan.getName() = " + spartan.getName());



    }

    @DisplayName("GET all spartan from Search class and use POJO")
    @Test
    public void test3(){
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/spartans/search")
                .then()
                .statusCode(200)
                .extract().response();

        //get the full content json and convert it to Search object
        Search searchResult = response.as(Search.class);

        System.out.println("searchResult.getTotalElement() = " + searchResult.getTotalElement());
        System.out.println("searchResult.getContent().get(1).getGender() = " + searchResult.getContent().get(1).getGender());
        System.out.println("searchResult.getContent().get(1).getName() = " + searchResult.getContent().get(1).getName());

        //we can also use jackson to get Search result
        Search search2 = response.jsonPath().getObject("",Search.class);



    }

    @DisplayName("GET /api/spartans/search and save as List<Spartan>")
    @Test
    public void test4(){
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/spartans/search")
                .then()
                .statusCode(200)
                .extract().response();

      JsonPath jsonPath = response.jsonPath();

        List<Spartan> content = jsonPath.getList("content",Spartan.class);

        System.out.println("content.get(1).getName() = " + content.get(1).getName());

    }







}
