package pt.alticelabs.challenge;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class LabseqResourceTest {

    @Test
    void testLabseqEndpoint() {
        long index = 10;

        given()
                .when().get("/labseq/{n}", index)
                .then()
                .statusCode(200)
                .body("index", equalTo((int) index))
                .body("value", equalTo(3));
    }

    @Test
    void testNegativeIndexReturnsError() {
        long negativeIndex = -5;

        given()
                .when().get("/labseq/{n}", negativeIndex)
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("error", equalTo("Index must be non-negative."));
    }
}