package admin;

import admin.autorization.Auth;
import admin.permissionToRole.PermissionToRole;
import admin.permissions.Permissions;
import admin.role.Roles;
import admin.roleToUser.RoleToUser;
import admin.testData.PermissionsData;
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
public class TestRoleToUser extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Roles roles = new Roles();
    Users users = new Users();
    RoleToUser roleToUser = new RoleToUser();
    int idLastRole;
    int idLastUser;
    String token;

    @BeforeClass(description = "POST request for getting token")
    public void requestListOfRolesAndUser() throws IOException {
        request = getToken();
        request.body(users.addParamToBodyForCreareUser(UsersData.USER_ROLE));
        request.post(RequestURI.REGISTER_USER_URI);

        request.body(roles.addParamToBodyForCreareRole(RolesData.ROLE_USER));
        request.post(RequestURI.ROLES_URI);

        Response response = request.get(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        idLastRole = roles.getModels().get(roles.getModels().size() - 1).getId();
        response = request.get(RequestURI.USERS_URI);
        jsonObject = new JSONObject(response.asString());
        users = mapper.readValue(jsonObject.toString(), Users.class);
        idLastUser = users.getModels().get(users.getModels().size() - 1).getId();
    }

    @AfterClass(description = "POST request for getting token")
    public void delete() throws IOException {
        request = getToken();

        request.delete(RequestURI.USERS_URI + idLastUser);
        request.delete(RequestURI.ROLES_URI + idLastRole);
    }

    @Test()
    public void getRoleForUserWithId() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idLastUser + RequestURI.ROLES);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void addRoleForUserWithId() throws IOException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("role_id", idLastRole);
        request.body(requestBody.toString());
        Response response = request.post(RequestURI.USERS_URI + idLastUser + RequestURI.ROLES);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void successAddingRoleForUserWithId() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idLastUser + RequestURI.ROLES);
        JSONObject jsonObject = new JSONObject(response.asString());
        roleToUser= mapper.readValue(jsonObject.toString(), RoleToUser.class);
        Assert.assertEquals(roleToUser.getRoles().get(0).getDisplayName(), RolesData.ROLE_USER.getDisplay_name());
    }

    @Test()
    public void successSavingRoleForUserWithIdAfterUpdateUser() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idLastUser + RequestURI.ROLES);
        JSONObject jsonObject = new JSONObject(response.asString());
        roleToUser = mapper.readValue(jsonObject.toString(), RoleToUser.class);
        Assert.assertEquals(roleToUser.getRoles().get(0).getDisplayName(), RolesData.TEST_UPDATE_NAME.getDisplay_name());
    }

    @Test()
    public void getRoleByIdForUserWithId() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idLastUser + RequestURI.ROLES + idLastRole);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void errorGetRoleByIdForUserWithId() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idLastUser + RequestURI.ROLES + (idLastRole + 1));
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test()
    public void deleteRoleByIdForUserWithId() throws IOException {
        Response response = request.delete(RequestURI.USERS_URI + idLastUser + RequestURI.ROLES + idLastRole);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void getRoleForUserWithIdAfterDelete() throws IOException {
        Response response = request.get(RequestURI.USERS_URI + idLastUser + RequestURI.ROLES);
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
