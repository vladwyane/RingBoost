package base;

import admin.Credentials;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Listeners;

/**
 * Created by bigdrop on 12/18/2018.
 */
@Listeners(LogListener.class)
public class TestBase {

    public RequestSpecification baseAuth(Credentials credentials) {
        return RestAssured.given().auth().basic(credentials.getEmail(), credentials.getPassword());
    }

    public RequestSpecification authWithToken(String token) {
        return RestAssured.given().auth().oauth2(token);
    }
}
