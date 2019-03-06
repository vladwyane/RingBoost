package admin;

import admin.permissionToRole.PermissionToRole;
import admin.permissions.Permissions;
import admin.permissions.currentPermission.CurrentPermission;
import admin.role.Roles;
import admin.role.currrentRole.CurrentRole;
import admin.testData.PermissionsData;
import admin.testData.RolesData;
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
 * Created by bigdrop on 2/18/2019.
 */
public class TestsPermissionsToRole extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    PermissionToRole permissionToRole = new PermissionToRole();
    CurrentRole currentRole = new CurrentRole();
    CurrentPermission currentPermission = new CurrentPermission();
    int idCreatedRole;
    int idCreatedPermission;


    @BeforeClass(description = "POST request for getting token")
    public void requestListOfRolesAndPermissions() throws IOException {
        request = getToken();

        request.body(currentRole.addParamToBodyForCreareRole(RolesData.ROLE_PERMISSION));
        Response response = request.post(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentRole = mapper.readValue(jsonObject.toString(), CurrentRole.class);
        idCreatedRole = currentRole.getModel().getId();

        request.body(currentPermission.addParamToBodyForCrearePermission(PermissionsData.PERMISSION_ROLE));
        response = request.post(RequestURI.PERMISSIONS_URI);
        jsonObject = new JSONObject(response.asString());
        currentPermission = mapper.readValue(jsonObject.toString(), CurrentPermission.class);
        idCreatedPermission = currentPermission.getModel().getId();
    }

    @AfterClass(description = "POST request for getting token")
    public void delete() throws IOException {
        request.delete(RequestURI.PERMISSIONS_URI + idCreatedPermission);
        request.delete(RequestURI.ROLES_URI + idCreatedRole);
    }

    @Test()
    public void addPermissionsForRoleWithId() throws IOException {
        int[] arr = {idCreatedPermission};
        JSONObject requestBody = new JSONObject();
        requestBody.put("permissions", arr);
        request.body(requestBody.toString());
        Response response = request.post(RequestURI.ROLES_URI + idCreatedRole + RequestURI.PERMISSION);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void successAddingPermissionsForRoleWithId() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI + idCreatedRole + RequestURI.PERMISSION);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissionToRole = mapper.readValue(jsonObject.toString(), PermissionToRole.class);
        Assert.assertEquals(permissionToRole.getPermissions().get(0).getId(), idCreatedPermission);
    }

    @Test()
    public void getPermissionByIdForRoleWithId() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI + idCreatedRole + RequestURI.PERMISSION + idCreatedPermission);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void errorGetPermissionByIdForRoleWithId() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI + idCreatedRole + RequestURI.PERMISSION + (idCreatedPermission + 1));
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test()
    public void deletePermissionByIdForRoleWithId() throws IOException {
        Response response = request.delete(RequestURI.ROLES_URI + idCreatedRole + RequestURI.PERMISSION + idCreatedPermission);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void getPermissionsForRoleWithIdAfterDelete() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI + idCreatedRole + RequestURI.PERMISSION);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissionToRole = mapper.readValue(jsonObject.toString(), PermissionToRole.class);
        Assert.assertEquals(permissionToRole.getPermissions().size(), 0);
    }
}
