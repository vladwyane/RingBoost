package admin;

import admin.autorization.Auth;
import admin.testData.UsersData;
import admin.users.Users;
import base.TestBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by bigdrop on 2/22/2019.
 */
public class Test100Requests extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Users users = new Users();
    int idLastUser;
    int amountUserssBefore;
    int amountUsersAfter;

    @BeforeClass(description = "POST request for getting token")
    public void getTokenAndList() throws IOException, InterruptedException {
        Thread.sleep(5000);
        String token = Auth.getToken(UsersData.ADMIN);
        request = authWithToken(token);
        request.header("Content-Type", "application/json");
    }

    @Test()
    public void getListOfUsers() throws IOException {
        int count = 0;
        String name = null;
        for (int i = 0; i < 60; i++) {
            Response response = request.get(RequestURI.USERS_URI);
            if (i == 58) {
                count = response.statusCode();
                System.out.println(count);
            }
        }

    }

}
