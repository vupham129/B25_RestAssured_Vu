package com.cydeo.day7;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpartanPostRequestDemo extends SpartanTestBase {
        /*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"Severus",
      "phone":8877445596
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response/body should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */
    @DisplayName("POST a spartan with String json body")
    @Test
    public void test1(){

        String requestBody = " {\n" +
                "      \"gender\":\"Male\",\n" +
                "      \"name\":\"Severus\",\n" +
                "      \"phone\":8877445596\n" +
                "   }";
       Response response = given()
                .accept(ContentType.JSON).log().all() // what we are asking from api which is json response
                .and()
                .contentType(ContentType.JSON) // what we are sending to api, which is json request body
                .body(requestBody)
                .when().post("/api/spartans");
//                .then()
//                .statusCode(201)
//                .and()
//                .contentType("application/json");
              //  .body("success",is("A Spartan is Born!"), );

        //verify status code
        assertThat(response.statusCode(),is(201));
        assertThat(response.contentType(),is("application/json"));

        String expectedMessage = "A Spartan is Born!";
        assertThat(response.path("success"),is(expectedMessage));
        assertThat(response.path("data.name"),is("Severus"));
        assertThat(response.path("data.gender"),is("Male"));
        assertThat(response.path("data.phone"),is(8877445596l));



    }

    @DisplayName("POST a spartan with Map")
    @Test
    public void test2(){

        Map<String,Object> requestMap = new LinkedHashMap<>();
        requestMap.put("gender","Male");
        requestMap.put("name","Severus Snape");
        requestMap.put("phone",8877445596l);


        Response response = given()
                .accept(ContentType.JSON) // what we are asking from api which is json response
                .and()
                .contentType(ContentType.JSON) // what we are sending to api, which is json request body
                .body(requestMap)
                .when().post("/api/spartans");


        //verify status code
        assertThat(response.statusCode(),is(201));
        assertThat(response.contentType(),is("application/json"));

        String expectedMessage = "A Spartan is Born!";
        assertThat(response.path("success"),is(expectedMessage));
        assertThat(response.path("data.name"),is("Severus Snape"));
        assertThat(response.path("data.gender"),is("Male"));
        assertThat(response.path("data.phone"),is(8877445596l));



    }

    @DisplayName("POST a spartan with Spartan class")
    @Test
    public void test3(){

      //create one object from your pojo, send it as a json
        Spartan spartan = new Spartan();
        spartan.setGender("Male");
        spartan.setName("Severus Snape");
        spartan.setPhone(8877445596l);


        Response response = given()
                .accept(ContentType.JSON).log().all() // what we are asking from api which is json response
                .and()
                .contentType(ContentType.JSON) // what we are sending to api, which is json request body
                .body(spartan)
                .when().post("/api/spartans");

        //verify status code
        assertThat(response.statusCode(),is(201));
        assertThat(response.contentType(),is("application/json"));

        String expectedMessage = "A Spartan is Born!";
        assertThat(response.path("success"),is(expectedMessage));
        assertThat(response.path("data.name"),is("Severus Snape"));
        assertThat(response.path("data.gender"),is("Male"));
        assertThat(response.path("data.phone"),is(8877445596l));



    }

    @DisplayName("POST a spartan with Spartan class")
    @Test
    public void test4(){

        //create one spartan with Spartan object
        //POST it
        //get id number dynamically
        //send a get request
        //save information in spartan object
        //assert that information  matched


        //create one object from your pojo, send it as a json
        Spartan spartanPost = new Spartan();
        spartanPost.setGender("Male");
        spartanPost.setName("Bruce Wayne");
        spartanPost.setPhone(8877445596l);


        int idFromPost = given()
                .accept(ContentType.JSON).log().all() // what we are asking from api which is json response
                .and()
                .contentType(ContentType.JSON) // what we are sending to api, which is json request body
                .body(spartanPost)
                .when().post("/api/spartans")
                 .then()
                 .statusCode(201)
                 .contentType("application/json")
                 .body("success",is("A Spartan is Born!"))
                 .extract().jsonPath().getInt("data.id");

        System.out.println("idFromPost = " + idFromPost);

       Spartan spartanGet = given().accept(ContentType.JSON)
                .and().pathParam("id",idFromPost)
                .when().get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .extract().response().as(Spartan.class);

       assertThat(spartanGet.getName(),is(spartanPost.getName()));


    }

}
