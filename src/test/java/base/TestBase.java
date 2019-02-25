package base;

import admin.RequestURI;
import admin.autorization.Auth;
import admin.permissions.Permissions;
import admin.testData.UsersData;
import admin.users.Users;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.io.IOException;

/**
 * Created by bigdrop on 12/18/2018.
 */
@Listeners(LogListener.class)
public class TestBase {

    @BeforeClass(description = "POST request for getting token")
    public RequestSpecification getToken() throws IOException {
        RequestSpecification request;
        String token = Auth.getToken(UsersData.ADMIN);
        request = authWithToken(token);
        request.header("Content-Type", "application/json");
        return request;
    }

    public RequestSpecification baseAuth(UsersData usersData) {
        return RestAssured.given().auth().basic(usersData.getEmail(), usersData.getPassword());
    }

    public RequestSpecification authWithToken(String token) {
        return RestAssured.given().auth().oauth2(token);
    }
}
