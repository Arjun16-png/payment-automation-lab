package payment;

import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class CreateChargeTest {
    
    @Test
    public void healthCheckTest() {
        RestAssured
            .given()
            .when()
            .get("https://jsonplaceholder.typicode.com/posts/1")
            .then()
            .statusCode(200)
            .body("id", equalTo(1));
    }
}