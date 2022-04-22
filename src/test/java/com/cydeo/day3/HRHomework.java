package com.cydeo.day3;

import com.cydeo.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HRHomework extends HRTestBase {
    /*
    ORDS API:
Q1:
- Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is 2
*/
    @DisplayName("GET request /countries with Query Param and path()")
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id","US")
                .when().get("/countries/{id}");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.header("Content-Type"));
        JsonPath jsonPath = response.jsonPath();
        String country_id = jsonPath.getString("country_id");
        String country_name = jsonPath.getString("country_name");
        int region_id = jsonPath.getInt("region_id");

        assertEquals("US",country_id);
        assertEquals("United States of America", country_name);
        assertEquals(2,region_id);

    }

/*
Q2:
- Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
*/
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q","{\"department_id\":80}")
                .when().get("/employees");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.header("Content-Type"));

        JsonPath jsonPath = response.jsonPath();
        //all job_ids start with 'SA'
        List<String> jobIDStartWithSA = jsonPath.getList("items.findAll {it.job_id.startsWith(\"SA\")}.job_id");
        System.out.println("jobIDStartWithSA = " + jobIDStartWithSA);

        for (String s : jobIDStartWithSA) {
            assertEquals(true,s.startsWith("SA"));
        }

        //all department_ids are 80
        List<Integer> department_ids = jsonPath.getList("items.findAll {it.department_id == 80}.department_id");
        System.out.println("department_ids = " + department_ids);
        for (Integer each : department_ids) {
            assertEquals(80,each);
        }

        //Count is 25
        int count = response.path("count");
        System.out.println("count: "+count);
        assertEquals(25,count);

    }



    /*
Q3:
- Given accept type is Json
-Query param value q= region_id 3
- When users sends request to /countries
- Then status code is 200
- And all regions_id is 3
- And count is 6
- And hasMore is false
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore
     */

    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q","{\"region_id\":3}")
                .when().get("/countries");

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.header("Content-Type"));
        //count is 6
        int count = response.path("count");

        JsonPath jsonPath = response.jsonPath();
        //all regions_id is 3
        List<Integer> region_ids = jsonPath.getList("items.findAll {it.region_id ==3}.region_id");
        for (Integer each : region_ids) {
            assertEquals(3,each);
        }

        //Country_name are;
        //Australia,China,India,Japan,Malaysia,Singapore
        List<String> country_names = jsonPath.getList("items.country_name");
        System.out.println("country_names = " + country_names);

        List<String> expectedCountryNames = new ArrayList<>(Arrays.asList( "Australia", "China", "India", "Japan", "Malaysia", "Singapore"));
        System.out.println("expectedCountryNames = " + expectedCountryNames);

        assertEquals(expectedCountryNames,country_names);


    }




}
