package com.cydeo.day5;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
public class JSONtoJAVATest extends SpartanTestBase {

    @DisplayName("GET one Spartan and deserialize to MAP")
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id",15)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        //get the json body and convert it ti java map
        Map<String, Object> jsonMap = response.as(Map.class);

        System.out.println(jsonMap);

        //Jackson, Gson, Json

        //verify name is Meta
        String name = (String) jsonMap.get("name");

        assertThat(name,is("Meta"));

    }

    @DisplayName("GET all spartans to JAVA data structure")
    @Test
    public void test2(){

        Response response = get("/api/spartans/").then().statusCode(200).extract().response();

        //we need to converst json to java which is deserialize
        List<Map<String,Object>> jsonList = response.as(List.class);

        System.out.println("jsonList.get(15).get(\"gender\") = " + jsonList.get(15).get("gender"));

        //return list of value of the map at index number 2
        System.out.println(jsonList.get(2).values());


    }











}
