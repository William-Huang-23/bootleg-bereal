package com.william.bootleg_bereal.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;


import java.util.Date;
import java.util.Random;

import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TestUserController {
    @BeforeAll
    public static void  setUp() {
        RestAssured.baseURI = "http://localhost:8000/usercontrol";
    }

    @Order(0)
    @Test
    public void getAllUsersSuccess() {
        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .get("/getallusers")
                .then().statusCode(200)
                .body("errorCode", equalTo(0));
    }

    @Order(1)
    @Test
    public void getUserSuccess() {
        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .get("/getuser?username=AutomatedUser0")
                .then().statusCode(200)
                .body("errorCode", equalTo(0));
    }

    @Order(2)
    @Test
    public void getUserError3() {
        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .get("/getuser?username=")
                .then().statusCode(200)
                .body("errorCode", equalTo(3));
    }

    @Order(3)
    @Test
    public void getUserError6() {
        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .get("/getuser?username=UserNotFound")
                .then().statusCode(200)
                .body("errorCode", equalTo(6));
    }

    @Order(4)
    @Test
    public void createUserSuccess() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser1");
        request.put("password", "StrongPassword123");
        request.put("name", "Automated User 1");
        request.put("age", 100);
        request.put("birthday", "17081945");

        RestAssured.given()
//                .header("Content-Type", "application/json")
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().post("/createuser")
                .then().statusCode(200)
                .body("errorCode", equalTo(0));
    }

    @Order(5)
    @Test
    public void createUserError3() throws JSONException {
        JSONObject request = new JSONObject();

//        request.put("username", "AutomatedUser1");
        request.put("password", "StrongPassword123");
        request.put("name", "No Username Field");
        request.put("age", 100);
        request.put("birthday", "17081945");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().post("/createuser")
                .then().statusCode(200)
                .body("errorCode", equalTo(3));
    }

    @Order(6)
    @Test
    public void createUserError7() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("password", "StrongPassword123");
        request.put("name", "Automated User 0");
        request.put("age", 12);
        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().post("/createuser")
                .then().statusCode(200)
                .body("errorCode", equalTo(7));
    }

    @Order(7)
    @Test
    public void createUserError14() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
//        request.put("password", "StrongPassword123");
        request.put("name", "Automated User 0");
        request.put("age", 12);
        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().post("/createuser")
                .then().statusCode(200)
                .body("errorCode", equalTo(14));
    }

    @Order(8)
    @Test
    public void createUserError15() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("password", "StrongPassword123");
//        request.put("name", "Automated User 0");
        request.put("age", 12);
        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().post("/createuser")
                .then().statusCode(200)
                .body("errorCode", equalTo(15));
    }

    @Order(9)
    @Test
    public void createUserError16() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("password", "StrongPassword123");
        request.put("name", "Automated User 0");
//        request.put("age", 12);
        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().post("/createuser")
                .then().statusCode(200)
                .body("errorCode", equalTo(16));
    }

    @Order(10)
    @Test
    public void createUserError17() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("password", "StrongPassword123");
        request.put("name", "Automated User 0");
        request.put("age", 12);
//        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().post("/createuser")
                .then().statusCode(200)
                .body("errorCode", equalTo(17));
    }

    @Order(11)
    @Test
    public void deleteUserSuccess() {
        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .when().delete("/deleteuser?username=AutomatedUser1")
                .then().statusCode(200)
                .body("errorCode", equalTo(0));
    }

    @Order(12)
    @Test
    public void deleteUserError3() {
        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .when().delete("/deleteuser?username=")
                .then().statusCode(200)
                .body("errorCode", equalTo(3));
    }

    @Order(13)
    @Test
    public void deleteUserError6() {
        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .when().delete("/deleteuser?username=UserNotFound")
                .then().statusCode(200)
                .body("errorCode", equalTo(6));
    }

    @Order(14)
    @Test
    public void updateUserProfileSuccess() throws JSONException {
        JSONObject request = new JSONObject();

        int randomNumber = new Random().nextInt(10000);

        request.put("username", "AutomatedUser0");
        request.put("name", "Name updated via test: " + randomNumber);
        request.put("age", 12);
        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/updateuserprofile")
                .then().statusCode(200)
                .body("errorCode", equalTo(0))
                .body("data.name", equalTo("Name updated via test: " + randomNumber));
    }

    @Order(15)
    @Test
    public void updateUserProfileError3() throws JSONException {
        JSONObject request = new JSONObject();

//        request.put("username", "AutomatedUser0");
        request.put("name", "NoUserName");
        request.put("age", 12);
        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/updateuserprofile")
                .then().statusCode(200)
                .body("errorCode", equalTo(3));
    }

    @Order(16)
    @Test
    public void updateUserProfileError6() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "UserNotFound");
        request.put("name", "User Not Found");
        request.put("age", 12);
        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/updateuserprofile")
                .then().statusCode(200)
                .body("errorCode", equalTo(6));
    }

    @Order(17)
    @Test
    public void updateUserProfileError15() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
//        request.put("name", "Name updated via test");
        request.put("age", 12);
        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/updateuserprofile")
                .then().statusCode(200)
                .body("errorCode", equalTo(15));
    }

    @Order(18)
    @Test
    public void updateUserProfileError16() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("name", "Name updated via test");
//        request.put("age", 12);
        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/updateuserprofile")
                .then().statusCode(200)
                .body("errorCode", equalTo(16));
    }

    @Order(19)
    @Test
    public void updateUserProfileError17() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("name", "Name updated via test");
        request.put("age", 12);
//        request.put("birthday", "23072000");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/updateuserprofile")
                .then().statusCode(200)
                .body("errorCode", equalTo(17));
    }

    @Order(20)
    @Test
    public void addFriendSuccess() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "AutomatedUser99");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/addfriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(0))
                .body("data[0].friendList.findAll { it == 'AutomatedUser99'}.size()", greaterThan(0))
                .body("data[1].friendList.findAll { it == 'AutomatedUser0'}.size()", greaterThan(0));
    }

    @Order(21)
    @Test
    public void addFriendError11() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "AutomatedUser99");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/addfriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(11));
    }

    @Order(22)
    @Test
    public void addFriendError3() throws JSONException {
        JSONObject request = new JSONObject();

//        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "AutomatedUser99");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/addfriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(3));
    }

    @Order(23)
    @Test
    public void addFriendError18() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
//        request.put("targetUsername", "AutomatedUser99");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/addfriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(18));
    }

    @Order(24)
    @Test
    public void addFriendError10() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "AutomatedUser0");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/addfriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(10));
    }

    @Order(25)
    @Test
    public void addFriendError6() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "UserNotFound");
        request.put("targetUsername", "AutomatedUser0");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/addfriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(6));
    }

    @Order(26)
    @Test
    public void addFriendError19() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "UserNotFound");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/addfriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(19));
    }

    @Order(27)
    @Test
    public void removeFriendSuccess() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "AutomatedUser99");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/removefriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(0))
                .body("data[0].friendList.findAll { it == 'AutomatedUser99'}.size()", equalTo(0))
                .body("data[1].friendList.findAll { it == 'AutomatedUser0'}.size()", equalTo(0));
    }

    @Order(28)
    @Test
    public void removeFriendError12() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "AutomatedUser99");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/removefriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(12));
    }

    @Order(29)
    @Test
    public void removeFriendError3() throws JSONException {
        JSONObject request = new JSONObject();

//        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "AutomatedUser99");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/removefriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(3));
    }

    @Order(30)
    @Test
    public void removeFriendError18() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
//        request.put("targetUsername", "AutomatedUser99");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/removefriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(18));
    }

    @Order(31)
    @Test
    public void removeFriendError10() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "AutomatedUser0");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/removefriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(10));
    }

    @Order(32)
    @Test
    public void removeFriendError6() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "UserNotFound");
        request.put("targetUsername", "AutomatedUser0");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/removefriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(6));
    }

    @Order(33)
    @Test
    public void removeFriendError19() throws JSONException {
        JSONObject request = new JSONObject();

        request.put("username", "AutomatedUser0");
        request.put("targetUsername", "UserNotFound");

        RestAssured.given()
                .header(TestEnvVariable.apiKey, TestEnvVariable.apiValue)
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(request.toString())
                .when().put("/removefriend")
                .then().statusCode(200)
                .body("errorCode", equalTo(19));
    }
}
