package pt.alticelabs.challenge;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class LabseqResourceTest {

    @Test
    void testLabseqEndpoint() {
        given()
                .when().get("/labseq/{n}", 10)
                .then()
                .statusCode(200)
                .body("index", equalTo(10))
                .body("value", equalTo("3"));
    }

    @Test
    void testNegativeIndexReturnsError() {
        given()
                .when().get("/labseq/{n}", -5)
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("error", equalTo("Index must be non-negative."));
    }
}