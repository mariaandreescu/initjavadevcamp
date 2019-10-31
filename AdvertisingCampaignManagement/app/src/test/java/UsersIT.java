import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class UsersIT {

    @Test
    public void shouldGetUsers() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8181;

        Response response = given()
                .contentType(ContentType.JSON)
                .get("/users");

        List<Map<String,Object>> users
        = response.getBody().jsonPath().get("content");

        assertEquals(10, users.size());
    }
}
