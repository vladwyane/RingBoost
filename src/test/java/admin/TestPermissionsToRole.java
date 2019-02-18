package admin;

import admin.autorization.Auth;
import admin.permissionToRole.PermissionToRole;
import admin.permissions.Permissions;
import admin.role.Roles;
import admin.testData.PermissionsData;
import admin.testData.RolesData;
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
 * Created by bigdrop on 2/18/2019.
 */
public class TestPermissionsToRole extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Roles roles = new Roles();
    Permissions permissions = new Permissions();
    PermissionToRole permissionToRole = new PermissionToRole();
    int idLastRole;
    int idLastPermission;

    @BeforeClass(description = "POST request for getting token")
    public void getTokenAndListOfRolesAndPermissions() throws IOException {
        String token = Auth.getToken(Credentials.ADMIN);
        request = authWithToken(token);
        request.header("Content-Type", "application/json");
        Response response = request.get(RequestURI.ROLE_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        idLastRole = roles.getModels().get(roles.getModels().size() - 1).getId();
        response = request.get(RequestURI.PERMISSION_URI);
        jsonObject = new JSONObject(response.asString());
        permissions = mapper.readValue(jsonObject.toString(), Permissions.class);
        idLastPermission = permissions.getModels().get(permissions.getModels().size() - 1).getId();
    }

    @Test()
    public void getPermissionsForRoleWithId() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI + idLastRole + RequestURI.PERMISSION);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void addPermissionsForRoleWithId() throws IOException {
        int[] arr = {idLastPermission};
        JSONObject requestBody = new JSONObject();
        requestBody.put("permissions", arr);
        request.body(requestBody.toString());
        Response response = request.post(RequestURI.ROLE_URI + idLastRole + RequestURI.PERMISSION);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void successAddingPermissionsForRoleWithId() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI + idLastRole + RequestURI.PERMISSION);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissionToRole = mapper.readValue(jsonObject.toString(), PermissionToRole.class);
        Assert.assertEquals(permissionToRole.getPermissions().get(0).getDisplayName(), PermissionsData.ALL_CAN.getDisplay_name());
    }

    @Test()
    public void getPermissionByIdForRoleWithId() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI + idLastRole + RequestURI.PERMISSION + idLastPermission);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void errorGetPermissionByIdForRoleWithId() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI + idLastRole + RequestURI.PERMISSION + (idLastPermission + 1));
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test()
    public void deletePermissionByIdForRoleWithId() throws IOException {
        Response response = request.delete(RequestURI.ROLE_URI + idLastRole + RequestURI.PERMISSION + idLastPermission);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void getPermissionsForRoleWithIdAfterDelete() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI + idLastRole + RequestURI.PERMISSION);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissionToRole = mapper.readValue(jsonObject.toString(), PermissionToRole.class);
        Assert.assertEquals(permissionToRole.getPermissions().size(), 0);
    }
}
