package API.tempmail.adapter;

import com.google.gson.Gson;
import io.restassured.response.Response;

import static API.tempmail.utils.StringConstant.*;
import static io.restassured.RestAssured.given;

public class BaseAdapter {

    protected Gson converter = new Gson();


    public static Response requestGET(String url) {
        return given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .when()
                .get(BASE_URL + url)
                .then()
//                .log().all()
                .extract().response();
    }


    public static Response getWithAuth(String url, String token) {
        return given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(BASE_URL + url)
                .then()
//                .log().all()
                .extract().response();
    }

    public static Response postWithoutAuth(String url, String body) {
        return given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(body)
                .when()
                .post(BASE_URL + url)
                .then()
//                .log().all()
                .extract().response();
    }


    public Response delete(String token, String id) {
        return given()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(BASE_URL + "/account/" + id)
                .then()
//                .log().all()
                .extract().response();
    }

}
