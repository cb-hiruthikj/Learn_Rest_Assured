import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class APITest {
    String BASE_URI = "https://reqres.in";

    @BeforeClass
    void setup() {
        baseURI = BASE_URI;
        basePath = "/api";
    }

    @Test
    void test1() {
        Response res = get("/users?page=2");
        //        System.out.println(res.asPrettyString());
        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    void test2_jsonpath() {
        given()
                .queryParam("page", 2)
                .header("Content-Type", "application/json")
        .when()
                .get("/users")
        .then()
                .statusCode(200)
                // Hamcrest Matchers
                .body("data[0].last_name", equalTo("Lawson"))
                .log().all()
        ;
    }

    @Test
    void test3_post() {
        JSONObject reqBody = new JSONObject();
        reqBody.put("name", "morpheus");
        reqBody.put("job", "leader");

        given()
                .body(reqBody.toJSONString())
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .log().all()
        ;
    }

    @Test
    void test4_deserialisation() {
        JSONObject reqBody = new JSONObject();
        reqBody.put("email", "h@gmail.com");
        reqBody.put("password", "demopwd");

        RegisterPOJO obj =
                given()
                        .body(reqBody.toJSONString())
                        .when()
                        .post("api/register")
                        .getBody()
                        // Note that its before then
                        // requires jackson databind or gson in classpath
                        .as(RegisterPOJO.class)
                ;
        System.out.println(obj);
    }

    @Test
    void test5_deserialize_list() {
        List<UserPOJO> users=
        given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .jsonPath()
                .getList("data[1,2]", UserPOJO.class)
                ;

        for (UserPOJO user : users) {
            System.out.println(user);
        }
    }

    @Test
    void test6_deserialize_array() {
        UserPOJO[] users=
                given()
                        .queryParam("page", 2)
                        .when()
                        .get("/users")
                        .jsonPath()
                        // Use array notation "[]" to get as Array of POJOs
                        .getObject("data", UserPOJO[].class)
                ;

        for (UserPOJO user : users) {
            System.out.println(user);
        }
    }

    @Test
    void test_schema() {

    }
}
