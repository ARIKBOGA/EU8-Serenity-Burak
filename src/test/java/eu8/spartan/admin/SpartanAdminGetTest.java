package eu8.spartan.admin;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.is;

@Disabled
@SerenityTest
public class SpartanAdminGetTest {


    @BeforeAll
    public static void init() {
        baseURI = "http://54.234.226.200:7000";

        requestSpecification = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin", "admin");
        responseSpecification = expect().contentType(ContentType.JSON)
                .statusCode(200)
                .logDetail(LogDetail.ALL);
    }


    @Test
    public void getAllSpartan() {


        given().spec(requestSpecification).get("/api/spartans")
                .then().spec(responseSpecification);
    }

    @Test
    public void getOneSpartan() {

        SerenityRest.given(requestSpecification)
                .pathParam("id", 13)
                .spec(requestSpecification)
                .get("/api/spartans/{id}")
                .then()
                .spec(responseSpecification);

        // we can access the last version of the response from API with this method
        // which comes from SerenityRest class
        System.out.println("lastResponse().getTime() = " + lastResponse().getTime());

        Ensure.that("Status code: 200", resp -> {
            resp.statusCode(200);
            resp.body("id",is(13));
            resp.body("name",is("Jaimie"));
            resp.body("gender",is("Female"));
        });
    }
}