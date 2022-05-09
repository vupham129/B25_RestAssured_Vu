package com.cydeo.day10;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class JsonSchemaValidationTest extends SpartanAuthTestBase {

    @DisplayName("GET request to verify one spartan against to schema")
    @Test
    public void test1(){

        given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("id",10)
                .and()
                .auth().basic("admin","admin")
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json"))
                .log().all();



    }

    @Test
    public void test2(){

        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
                .when()
                .get("/api/spartans")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/java/com/cydeo/day10/allSpartanSchema.json")))
;
    }

    //homework
    //put your post json schema under 10
    //post one spartan using dynamic input(name,gender,phone)
    //verify your post response matching with post response

    // SEND GET REQUEST TO https://api.zippopotam.us/us/{zipcode}
    // with these zipcodes 22030,22031, 22032, 22033 , 22034, 22035, 22036
    // check status code 200

    @ParameterizedTest
    @ValueSource(ints = {22030,22031,22032,22033,22034,22035,22036})
    public void zipcodeTest(int zipcode){

        given()
                .accept(ContentType.JSON)
                .and()
                .pathParam("zipcode",zipcode)
                .when()
                .get("https://api.zippopotam.us/us/{zipcode}")
                .then()
                .statusCode(200);
    }

    //Test firstNumber + secondNumber = thirdNumber
    // 1,3,4
    //2,3,5
    //8,7,15
    //10,9,19

    @ParameterizedTest
    @CsvSource({"1,3,4" , "2,3,5", "8,7,15" ,"10,9,19"})
    public void testAddition(int num1, int num2, int sum){
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
        System.out.println("sum = " + sum);
        MatcherAssert.assertThat(num1+num2,is(equalTo(sum)));

    }

    // Write a parameterized test for this request
    // GET https://api.zippopotam.us/us/{state}/{city}
    /*
        "NY, New York",
        "CO, Denver",
        "VA, Fairfax",
        "VA, Arlington",
        "MA, Boston",
        "NY, New York",
        "MD, Annapolis"
     */
    //verify place name contains your city name
    //print number of places for each request

    @ParameterizedTest
    @CsvSource({"NY, New York",
            "CO, Denver",
            "VA, Fairfax",
            "VA, Arlington",
            "MA, Boston",
            "NY, New York",
            "MD, Annapolis"})
    public void stateCityTest(String state,String city){

        Response response = given()
                .baseUri("https://api.zippopotam.us")
                .accept(ContentType.JSON)
                .and()
                .pathParam("state", state)
                .and()
                .pathParam("city", city)
                .when()
                .get("/us/{state}/{city}")
                .then()
                .statusCode(200)
                .extract().response();


        //verify place name contains your city name

        String placeName = response.path("'place name'");

        Assertions.assertTrue(placeName.contains(city));

        //print number of places for each request

        int numOfPlaces = response.path("places.size()");

        System.out.println(city+" : " + numOfPlaces);


    }
















}
