package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    static {
        RestAssured.baseURI = BASE_URL;
    }

    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    public static Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete("/api/auth/user");
    }


    public static Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post("/api/auth/login");
    }
}
