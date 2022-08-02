package eu8.spartan;

import static io.restassured.RestAssured.*;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@SerenityTest
public class SpartanAdminGetTest {


    @BeforeAll
    public static void init(){
        baseURI = "http://54.234.226.200:7000";
    }

    @Test
    public void getAllSpartan(){

        requestSpecification = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .auth().basic("admin","admin");
        responseSpecification = expect().contentType(ContentType.JSON)
                        .statusCode(200)
                .logDetail(LogDetail.ALL);

        given().spec(requestSpecification).get("/api/spartans")
                .then().spec(responseSpecification);
    }
}
