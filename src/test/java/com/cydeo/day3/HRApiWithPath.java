package com.cydeo.day3;

import com.cydeo.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HRApiWithPath extends HRTestBase {

    @DisplayName("GET request to countries with path method")
    @Test
    public void test1(){
        Response response = given()
                .log().all().accept(ContentType.JSON)
                .queryParam( "q", "{\"region_id\":2}")
                .when().get("/countries");

        //response.prettyPrint();
        //print limits result
        System.out.println("limit = " + response.path("limit").toString());
        //print hasMore
        System.out.println("hasMore = " + response.path("hasMore"));
        //print second country id
        System.out.println("response.path(\"items[1].country_id\").toString() = " + response.path("items[1].country_id").toString());
        //print 4th country name
        System.out.println("response.path(\"items[3].country_name\").toString() = " + response.path("items[3].country_name").toString());

        //get me all country names
        List<String> countryNames = response.path("items.country_name");
        System.out.println(countryNames);

        //assert that in the response all region_ids are 2
        //save all the regions ids inside the list
        List<Integer> region_ids = response.path("items.region_id");
        System.out.println(region_ids);

        //assert one by one that they are equal to 2
        for (Integer eachRegion_id : region_ids) {
            assertEquals(2,eachRegion_id);
        }


    }

    /*
        send a GET request o employees endpoint, filter only Job_id IT_PROG
        then assert that all job_ids are IT_PROG
     */

    @DisplayName("GET request /employees with job_id IT_PROG")
    @Test
    public void test2(){

        Response response = given().queryParam("q", "{\"job_id\":\"IT_PROG\"}").when().get("/employees");

        List<String> job_ids = response.path("items.job_id");

        System.out.println("job_ids = " + job_ids);
        String expectedJOBID = "IT_PROG";

        for (String actualJob_id : job_ids) {
            assertEquals(expectedJOBID, actualJob_id);
        }


    }


}














