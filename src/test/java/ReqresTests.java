import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    public static final String BASE_URL = "https://reqres.in";

    @Test
    void getSingleUser() {
        given()
                .when()
                .get(BASE_URL + "/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2));
    }

    @Test
    void singleUserNotFound() {
        given()
                .when()
                .get(BASE_URL+"/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void deleteSingleUser() {
        given()
                .when()
                .delete(BASE_URL + "/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void successfulRegistration() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"," +
                        "\"password\": \"pistol\"}")
                .when()
                .post(BASE_URL+"/api/register")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }

    @Test
    void negativeRegistration() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"sydney@fife\"}")
                .when()
                .post(BASE_URL+"/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

}
