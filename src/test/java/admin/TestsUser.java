package admin;

import admin.autorization.Auth;
import admin.role.Roles;
import admin.testData.RolesData;
import admin.testData.UsersData;
import admin.users.Users;
import base.TestBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

/**
 * Created by bigdrop on 2/19/2019.
 */
public class TestsUser extends TestBase {

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
        Response response = request.get(RequestURI.USERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(), Users.class);
        amountUserssBefore = users.getModels().size();
    }

    @Test()
    public void getListOfUsers() throws IOException {
        Assert.assertEquals(users.getResult(), "success");
    }

    @Test()
    public void errorCreateUserWithInvalidName() throws IOException {
        request.body(users.addParamToBodyForCreareUser(UsersData.INVALID_NAME));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void errorCreateUserWithInvalidEmail() throws IOException {
        request.body(users.addParamToBodyForCreareUser(UsersData.INVALID_EMAIL));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void errorCreateUserWithInvalidPassword() throws IOException {
        request.body(users.addParamToBodyForCreareUser(UsersData.INVALID_PASSWORD));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void errorCreateUserWithInvalidConfirmPass() throws IOException {
        request.body(users.addParamToBodyForCreareUser(UsersData.INVALID_CONFIRM_PASSWORD));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void successCreateNewUser() throws IOException {
        request.body(users.addParamToBodyForCreareUser(UsersData.VLAD));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void errorCreateUserWithExistName() throws IOException {
        request.body(users.addParamToBodyForCreareUser(UsersData.VLAD));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void getDisplayEmailLastUser() throws IOException {
        Response response = request.get(RequestURI.USERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(), Users.class);
        String displayEmailLastRole = users.getModels().get(users.getModels().size() - 1).getEmail();
        Assert.assertEquals(displayEmailLastRole, UsersData.VLAD.getEmail());
    }

    @Test()
    public void getListOfRolesAfterCreateNewUser() throws IOException {
        Response response = request.get(RequestURI.USERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(), Users.class);
        amountUsersAfter = users.getModels().size();
        idLastUser = users.getModels().get(amountUsersAfter - 1).getId();
        Assert.assertEquals(amountUserssBefore + 1, amountUsersAfter);
    }

    @Test()
    public void getLastUserById() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idLastUser);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void successUpdateUser() throws IOException {
        request.body(users.addParamToBodyForUpdateUser(UsersData.VLAD_UPDATE));
        Response response = request.patch(RequestURI.USERS_URI + idLastUser);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void getEmailLastUserAfterUpdate() throws IOException {
        Response response = request.get(RequestURI.USERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(),Users.class);
        String displayEmailLastUser = users.getModels().get(users.getModels().size() - 1).getEmail();
        Assert.assertEquals(displayEmailLastUser, UsersData.VLAD_UPDATE.getEmail());
    }

    @Test()
    public void successDeleteUser() throws IOException {
        Response response = request.delete(RequestURI.USERS_URI + idLastUser);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void getListOfUsersAfterDelete() throws IOException {
        Response response = request.get(RequestURI.USERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(),Users.class);
        Assert.assertEquals(users.getModels().size(), amountUserssBefore);
    }
}
