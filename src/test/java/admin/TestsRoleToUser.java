package admin;

import admin.autorization.Auth;
import admin.role.Roles;
import admin.role.currrentRole.CurrentRole;
import admin.roleToUser.RoleToUser;
import admin.testData.RolesData;
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
public class TestsRoleToUser extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    CurrentRole currentRole = new CurrentRole();
    CurrentUser currentUser = new CurrentUser();
    RoleToUser roleToUser = new RoleToUser();
    int idCreatedRole;
    int idCreatedUser;
    String token;

    @BeforeClass(description = "POST request for getting token")
    public void requestListOfRolesAndUser() throws IOException {
        request = getToken();
        request.body(currentUser.addParamToBodyForCreareUser(UsersData.USER_ROLE));
        Response response = request.post(RequestURI.REGISTER_USER_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentUser = mapper.readValue(jsonObject.toString(), CurrentUser.class);
        idCreatedUser = currentUser.getSuccess().getId();

        request.body(currentRole.addParamToBodyForCreareRole(RolesData.ROLE_USER));
        response = request.post(RequestURI.ROLES_URI);
        jsonObject = new JSONObject(response.asString());
        currentRole = mapper.readValue(jsonObject.toString(), CurrentRole.class);
        idCreatedRole = currentRole.getModel().getId();
    }

    @AfterClass(description = "POST request for getting token")
    public void delete() throws IOException {
        request = getToken();

        request.delete(RequestURI.USERS_URI + idCreatedUser);
        request.delete(RequestURI.ROLES_URI + idCreatedRole);
    }

    @Test()
    public void addRoleForUserWithId() throws IOException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("role_id", idCreatedRole);
        request.body(requestBody.toString());
        Response response = request.post(RequestURI.USERS_URI + idCreatedUser + RequestURI.ROLES);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void successAddingRoleForUserWithId() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idCreatedUser + RequestURI.ROLES);
        JSONObject jsonObject = new JSONObject(response.asString());
        roleToUser= mapper.readValue(jsonObject.toString(), RoleToUser.class);
        Assert.assertEquals(roleToUser.getRoles().get(0).getDisplayName(), RolesData.ROLE_USER.getDisplay_name());
    }

    @Test()
    public void getRoleByIdForUserWithId() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idCreatedUser + RequestURI.ROLES + idCreatedRole);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void errorGetRoleByIdForUserWithId() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idCreatedUser + RequestURI.ROLES + (idCreatedRole + 1));
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test()
    public void deleteRoleByIdForUserWithId() throws IOException {
        Response response = request.delete(RequestURI.USERS_URI + idCreatedUser + RequestURI.ROLES + idCreatedRole);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void getRoleForUserWithIdAfterDelete() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idCreatedUser + RequestURI.ROLES);
        JSONObject jsonObject = new JSONObject(response.asString());
        roleToUser = mapper.readValue(jsonObject.toString(), RoleToUser.class);
        Assert.assertEquals(roleToUser.getRoles().size(), 0);
    }

    @Test()
    public void successLoginNewUser() throws IOException {
        token = Auth.getToken(UsersData.USER_ROLE);
        Assert.assertTrue(token != null);
    }

    @Test()
    public void successLogOut() throws IOException {
        Response response = request.post(RequestURI.LOGOUT_URI);
        Assert.assertEquals(response.statusCode(), 204);
    }

}
