package dat.Routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populator;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

class TripRoutesTest {

    private static Javalin app;

    @BeforeAll
    static void setUpAll() {
        HibernateConfig.setTest(true);
        baseURI = "http://localhost:7070/";
        app = ApplicationConfig.startServer(7070);
    }

    @BeforeEach
    void setUp() {
        Populator.populate();
    }

    @Test
    void getAll() {
        given()
                .when()
                .get("/trips")
                .then()
                .statusCode(200)
                .body("size()", is(5));
    }

    @Test
    void getById() {
        given()
                .when()
                .get("/trips/1")
                .then()
                .statusCode(200)
                .body(is(notNullValue()));
    }

    @Test
    void create() {
        given()
                .contentType("application/json")
                .body("{ \"startTime\": \"08:00:00\", \"endTime\": \"10:00:00\", \"startPosition\": \"Location A\", \"name\": \"Trip to Rome\", \"price\": 1500.0, \"category\": \"SEA\" }")
                .when()
                .post("/trips")
                .then()
                .statusCode(201)
                .body("startTime", equalTo("08:00:00"))
                .body("endTime", equalTo("10:00:00"))
                .body("startPosition", equalTo("Location A"))
                .body("name", equalTo("Trip to Rome"))
                .body("category", equalTo("SEA"));
    }

    @Test
    void update() {
        given()
                .contentType("application/json")
                .body("{ \"startTime\": \"09:00:00\", \"endTime\": \"11:00:00\", \"startPosition\": \"Location B\", \"name\": \"Trip to Paris\", \"price\": 1300.0, \"category\": \"SNOW\" }")
                .when()
                .put("/trips/1")
                .then()
                .statusCode(200)
                .body("startTime", equalTo("09:00:00"))
                .body("endTime", equalTo("11:00:00"))
                .body("startPosition", equalTo("Location B"))
                .body("name", equalTo("Trip to Paris"))
                .body("category", equalTo("SNOW"));
    }

    @Test
    void delete() {
        given()
                .when()
                .delete("/trips/1")
                .then()
                .statusCode(204);
    }

    @Test
    void addGuideToTrip() {
        given()
                .contentType("application/json")
                .when()
                .put("/trips/1/guides/1")
                .then()
                .statusCode(200);
    }

    @Test
    void populate() {
        Populator.populate();
        given()
                .when()
                .get("/trips")
                .then()
                .statusCode(200)
                .body("size()", is(5));
    }
}