package com.cydeo.day6;

import com.cydeo.pojo.Employee;
import com.cydeo.pojo.Region;
import com.cydeo.utilities.HRTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class HRPojoGetRequestTest extends HRTestBase {

    @Test
    public void test1(){

       JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .when().get("/regions")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        // we want to store second region under Region class object
        Region region2 = jsonPath.getObject("items[1]", Region.class);

//        System.out.println("region2.getRegion_id() = " + region2.getRegion_id());
//        System.out.println("region2.getRegion_name() = " + region2.getRegion_name());
//        System.out.println("region2.getLinks().get(0).getHref() = " + region2.getLinks().get(0).getHref());


    }

    @Test
    public void test2(){

        JsonPath jsonPath = get("/employees").then().statusCode(200).extract().jsonPath();

        Employee emp1 = jsonPath.getObject("items[0]", Employee.class);

        System.out.println("emp1.getJobId() = " + emp1.getJobId());
        System.out.println("emp1.getFirstname() = " + emp1.getFirstname());
        System.out.println("emp1.getLastName() = " + emp1.getLastName());
        System.out.println("emp1.getSalary() = " + emp1.getSalary());

    }




}
