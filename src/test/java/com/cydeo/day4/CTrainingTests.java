package com.cydeo.day4;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class CTrainingTests {

    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://api.cybertektraining.com" ;
    }

    //send a get request to student id 24103 as a path parameter and accept header application/json
    //verify status code=200
    // /content type=application/json;charset=UTF-8
    // /Content-Encoding = gzip
    //verify Date header exists
    //assert that
            /*
                firstName Karole
                batch 7
                major Master of Creative Arts
                emailAddress hassan.lebsack@hotmail.com
                companyName Legacy Integration Analyst
                street 6253 Willard Place
                zipCode 28524

                using JsonPath
             */
    @Test
    public void test(){
        Response response = given().accept(ContentType.JSON)
                        .and().pathParam("id",24103)
                        .when().get("/student/{id}");

        //verify status code
        assertEquals(200,response.statusCode());

        // verify content type
        assertEquals("application/json;charset=UTF-8",response.contentType());

        //verify content encoding
        assertEquals("gzip",response.header("Content-Encoding"));

        //verify Date Header exist
        assertEquals(true, response.headers().hasHeaderWithName("Date"));

        //payload/body verification
        JsonPath jsonPath = response.jsonPath();

        String expectedFN = "Karole";
        int expectedBatch = 7;
        String expectedMajor ="Master of Creative Arts";
        String expectedEmail ="hassan.lebsack@hotmail.com";
        String expectedCompanyName = "Legacy Integration Analyst";
        String expectedStreet = "6253 Willard Place";
        int expectedZipCode = 28524;

        String actualFN = jsonPath.getString("students[0].firstName");
        int actualBatch = jsonPath.getInt("students[0].batch");
        String actualMajor = jsonPath.getString("students[0].major");
        String actualEmail = jsonPath.getString("students[0].contact.emailAddress");
        String actualCompanyName = jsonPath.getString("students[0].company.companyName");
        String actualStreet = jsonPath.getString("students[0].company.address.street");
        int actualZipcode = jsonPath.getInt("students[0].company.address.zipCode");

        System.out.println(actualFN);
        System.out.println(actualBatch);
        System.out.println(actualCompanyName);
        System.out.println(actualEmail);
        System.out.println(actualStreet);
        System.out.println(actualZipcode);
        System.out.println(actualMajor);

        assertEquals(expectedFN,actualFN);
        assertEquals(expectedBatch,actualBatch);
        assertEquals(expectedMajor,actualMajor);
        assertEquals(expectedEmail,actualEmail);
        assertEquals(expectedCompanyName,actualCompanyName);
        assertEquals(expectedStreet,actualStreet);
        assertEquals(expectedZipCode,actualZipcode);

    }

}
