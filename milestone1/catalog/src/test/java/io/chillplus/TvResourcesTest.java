package io.chillplus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.vertx.core.json.JsonObject;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

@QuarkusTest
public class TvResourcesTest
{
    @Test()
    public void checkTvShowTitleIsNotBlank()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("title","");
        given().body(jsonObject.encode())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when().post("/api/tv")
                .then()
                .statusCode(400);

    }
    @Test

    public void createTvShow()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("title","titulo1");
        given().contentType(ContentType.JSON).body(jsonObject.encode())
                .post("/api/tv")
                .then()
                .statusCode(200)
                .body("title",Matchers.is("titulo1"));
    }
    @Test
    public void createTvShowWithId()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("title","titulo1");
        jsonObject.put("id",2l);
        given().contentType(ContentType.JSON).body(jsonObject.encode())
                .post("/api/tv")
                .then()
                .statusCode(400);

    }

    @Test
    @Order(4)
    public void getAllTvShows() throws InterruptedException
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("title","titulo1");
        given().contentType(ContentType.JSON).body(jsonObject.encode())
                .post("/api/tv")
                .then()
                .statusCode(200);


        given()
                .when().get("/api/tv")
                .then()
                .statusCode(200)
                .body("$", Matchers.hasSize(1));

    }
    @Test
    public void getOneTvShow() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.put("title","titulo2");
        given().contentType(ContentType.JSON).body(jsonObject.encode())
                .post("/api/tv")
                .then()
                .statusCode(200);



        given().get("/api/tv/1")
                .then()
                .statusCode(200)
                .body("id",Matchers.is(1));

    }

    @Test
    public void getNonExistingTvShow()
    {
        given().get("/api/tv/42")
                .then()
                .statusCode(404);

    }
    @Test
    public void deleteAllTvShows()
    {
        given().delete("/api/tv")
                .then()
                .body("$.size()",Matchers.is(0));
    }
    @Test
    public  void deleteOneTvShow()
    {

        JsonObject jsonObject = new JsonObject();
        jsonObject.put("title","titulo2");
        given().contentType(ContentType.JSON).body(jsonObject.encode())
                .post("/api/tv")
                .then()
                .statusCode(200);


        given().delete("/api/tv/1")
             .then()
             .statusCode(200);

    }
}
