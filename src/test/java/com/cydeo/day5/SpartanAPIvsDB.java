package com.cydeo.day5;

import com.cydeo.utilities.DBUtils;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class SpartanAPIvsDB extends SpartanTestBase {

    @DisplayName("Compare one spartan information api vs db")
    @Test
    public void test1(){

        int idSpartan = 15;

        Response response = given().
                    accept(ContentType.JSON)
                    .pathParam("id",idSpartan)
                .when()
                    .get("/api/spartans/{id}")
                .then()
                    .statusCode(200)
                    .extract().response();

        //how to convert json response to map
        Map<String,Object> apiMap = response.as(Map.class);

        System.out.println("apiMap = " + apiMap);

        //we need to get information from database
        //which db we will connect ? dependency oracle
        //we need connection String of spartan DB
        String query = "SELECT SPARTAN_ID,NAME,GENDER,PHONE\n" +
                "from SPARTANS\n" +
                "where SPARTAN_ID ="+ idSpartan;
        Map<String, Object> dbMap = DBUtils.getRowMap(query);

        System.out.println("dbMap = " + dbMap);


        //compare api vs db
        assertThat(apiMap.get("id").toString(), is(dbMap.get("SPARTAN_ID").toString()));
        assertThat(apiMap.get("name"), is(dbMap.get("NAME")));
        assertThat(apiMap.get("gender"), is(dbMap.get("GENDER")));
        assertThat(apiMap.get("phone").toString(), is(dbMap.get("PHONE").toString()));


    }



}
