package admin;

import admin.autorization.Auth;
import admin.testData.UsersData;
import admin.users.Users;
import admin.users.currentUser.CurrentUser;
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
    CurrentUser currentUser = new CurrentUser();
    int idCreatedUser;
    int amountUsersBefore;
    int amountUsersAfter;

    @BeforeClass(description = "POST request for getting token")
    public void headerOfRequest() throws IOException, InterruptedException {
        request = getToken();
    }

    @Test()
    public void getListOfUsers() throws IOException {
        Response response = request.get(RequestURI.USERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(), Users.class);
        amountUsersBefore = users.getModels().size();
        Assert.assertEquals(users.getResult(), "success");
    }

    @Test()
    public void errorCreateUserWithInvalidName() throws IOException {
        request.body(currentUser.addParamToBodyForCreareUser(UsersData.INVALID_NAME));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void errorCreateUserWithInvalidEmail() throws IOException {
        request.body(currentUser.addParamToBodyForCreareUser(UsersData.INVALID_EMAIL));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void errorCreateUserWithInvalidPassword() throws IOException {
        request.body(currentUser.addParamToBodyForCreareUser(UsersData.INVALID_PASSWORD));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void errorCreateUserWithInvalidConfirmPass() throws IOException {
        request.body(currentUser.addParamToBodyForCreareUser(UsersData.INVALID_CONFIRM_PASSWORD));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void successCreateNewUser() throws IOException {
        request.body(currentUser.addParamToBodyForCreareUser(UsersData.VLAD));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentUser = mapper.readValue(jsonObject.toString(), CurrentUser.class);
        idCreatedUser = currentUser.getSuccess().getId();
        Assert.assertEquals(currentUser.getSuccess().getName(), UsersData.VLAD.getName());
    }

    @Test()
    public void errorCreateUserWithExistEmail() throws IOException {
        request.body(currentUser.addParamToBodyForCreareUser(UsersData.VLAD));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void checkAmountUsersAfterCreateNewUser() throws IOException {
        Response response = request.get(RequestURI.USERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(), Users.class);
        amountUsersAfter = users.getModels().size();
        Assert.assertEquals(amountUsersBefore + 1, amountUsersAfter);
    }

    @Test()
    public void getCurrentUserById() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idCreatedUser);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void successUpdateCurrentUser() throws IOException {
        request.body(currentUser.addParamToBodyForUpdateUser(UsersData.VLAD_UPDATE));
        Response response = request.patch(RequestURI.USERS_URI + idCreatedUser);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void getEmailCurrentUserAfterUpdate() throws IOException {
        Response response = request.get(RequestURI.USERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(),Users.class);
        String emailCurrentUser = users.getModels().get(users.getElementOrderByUserId(idCreatedUser)).getEmail();
        Assert.assertEquals(emailCurrentUser, UsersData.VLAD_UPDATE.getEmail());
    }

    @Test()
    public void successDeleteUser() throws IOException {
        Response response = request.delete(RequestURI.USERS_URI + idCreatedUser);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void errorUserByIdAfterDelete() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idCreatedUser);
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test()
    public void getListOfUsersAfterDelete() throws IOException {
        Response response = request.get(RequestURI.USERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(),Users.class);
        Assert.assertEquals(users.getModels().size(), amountUsersBefore);
    }
}
