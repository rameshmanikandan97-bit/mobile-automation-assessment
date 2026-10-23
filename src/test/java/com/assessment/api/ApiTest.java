package com.assessment.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/** Run with: mvn test -Dtest=ApiTest (public demo API; may be flaky - see README) */
@Tag("api")
class ApiTest {

    @BeforeAll
    static void setup() { RestAssured.baseURI = "https://jsonplaceholder.typicode.com"; }

    @Test
    void getPost_returns200() {
        given().when().get("/posts/1").then().statusCode(200).body("id", equalTo(1));
    }

    @Test
    void createPost_returns201() {
        given().header("Content-Type", "application/json")
               .body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}")
               .when().post("/posts").then().statusCode(201).body("id", notNullValue());
    }

    @Test
    void unknownPost_returns404() {
        given().when().get("/posts/99999").then().statusCode(404);
    }
}
