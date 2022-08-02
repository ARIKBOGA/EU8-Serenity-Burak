package eu8.spartan.editor;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utilities.SpartanNewBase;
import utilities.SpartanUtil;

import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.*;


@SerenityTest
public class SpartanEditorPostTest extends SpartanNewBase {

    @Disabled
    @Test
    public void postSpartansAsEditor() {

        //when you need deserialize or serialize, you dont need to add separate dependency, it comes
        //with serenity
        //create one spartan using util
        Map<String, String> bodyMap = SpartanUtil.getRandomSpartanMap();

        System.out.println("bodyMap = " + bodyMap);

        //send a post request as editor
        given()
                .auth().basic("editor", "editor")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post("/spartans")
                .then()
                .log().all();

        String id = lastResponse().jsonPath().getString("data.id");

        Ensure.that("Complete Check ", response -> {

            response.statusCode(201);
            response.contentType(ContentType.JSON);
            response.body("success", is("A Spartan is Born!"));
            response.body("data.id", is(notNullValue()));
            response.body("data.name", is(bodyMap.get("name")));
            response.body("data.gender", is(bodyMap.get("gender")));
            response.body("data.phone", is(Long.parseLong(bodyMap.get("phone"))));
            response.header("Location", is(endsWith(id)));

        });

    }


    @ParameterizedTest(name = "New Spartan {index} - {0}")
    @CsvFileSource(resources = "/SpartanData.csv", numLinesToSkip = 1)
    public void test2(String name, String gender, String phone) {


        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        bodyMap.put("gender", gender);
        bodyMap.put("phone", phone);


        given()
                .auth().basic("editor", "editor")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post("/spartans")
                .then();

        String id = lastResponse().jsonPath().getString("data.id");

        Ensure.that("Complete Check ", response -> {

            response.statusCode(201);
            response.contentType(ContentType.JSON);
            response.body("success", is("A Spartan is Born!"));
            response.body("data.id", is(notNullValue()));
            response.body("data.name", is(bodyMap.get("name")));
            response.body("data.gender", is(bodyMap.get("gender")));
            response.body("data.phone", is(Long.parseLong(bodyMap.get("phone"))));
            response.header("Location", is(endsWith(id)));

        });

    }
}