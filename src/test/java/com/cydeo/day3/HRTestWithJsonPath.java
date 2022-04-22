package com.cydeo.day3;

import com.cydeo.utilities.HRTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HRTestWithJsonPath extends HRTestBase {

    @Test
    public void test1(){
        Response response = get("/countries");

        //items[3].country_name

        //we want to use JsonPath to get that value
        JsonPath jsonPath = response.jsonPath();

        String countryName = jsonPath.getString("items[3].country_name");

        System.out.println("countryName = " + countryName); // Brazil

        //get all country names
        //items.country_name

        List<String> countryNames = jsonPath.getList("items.country_name");
        System.out.println("countryNames = " + countryNames);

        //print countries one by one
        for (String name : countryNames) {
            System.out.println(name);
        }

        //get me all country names where their region id is 2
        List<String> countryNamesRegionIDsIs2 = jsonPath.getList("items.findAll {it.region_id==2}.country_name");

        System.out.println("countryNamesRegionIDsIs2 = " + countryNamesRegionIDsIs2);

    }

    @DisplayName("GET request to /employees with param")
    @Test
    public void test2(){

        Response response = given().queryParam("limit",150).when().get("/employees");

        //get jsonpath object
        JsonPath jsonPath = response.jsonPath();

        // get me all emails who is working as iI_prog
        List<String> emailWhoIsITPROG = jsonPath.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");

        System.out.println("emailWhoIsITPROG = " + emailWhoIsITPROG);

        //get me first name of employees who is making more than 10000
        List<String> firstNameSalaryMoreThan10k = jsonPath.getList("items.findAll {it.salary>10000}.first_name");

        System.out.println("firstNameSalaryMoreThan10k = " + firstNameSalaryMoreThan10k);

        //get me the first name of who is making the highest salary
        String firstNameHighestSalary = jsonPath.getString("items.max {it.salary}.first_name");
        System.out.println("firstNameHighestSalary = " + firstNameHighestSalary);
        String firstNameLowestSalary = jsonPath.getString("items.min {it.salary}.first_name");

        System.out.println("firstNameLowestSalary = " + firstNameLowestSalary);

        System.out.println(response.path("items.max {it.salary}.first_name").toString());

    }




}

