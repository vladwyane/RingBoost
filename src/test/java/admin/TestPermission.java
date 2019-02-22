package admin;

import admin.autorization.Auth;
import admin.permissions.Permissions;
import admin.testData.PermissionsData;
import admin.testData.UsersData;
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
public class TestPermission extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Permissions permissions = new Permissions();
    int idLastPermissions;
    int amountPermissionsBefore;
    int amountPermissionsAfter;


    @BeforeClass(description = "POST request for getting token")
    public void getTokenAndListOf() throws IOException {
        String token = Auth.getToken(UsersData.ADMIN);
        request = authWithToken(token);
        request.header("Content-Type", "application/json");
        Response response = request.get(RequestURI.PERMISSIONS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissions = mapper.readValue(jsonObject.toString(), Permissions.class);
        amountPermissionsBefore = permissions.getModels().size();
    }

    @Test()
    public void getListOfPermissions() throws IOException {
        Assert.assertEquals(permissions.getResult(), "success");
    }

    @Test()
    public void successCreatePermission() throws IOException {
        request.body(permissions.addParamToBodyForCrearePermissijn(PermissionsData.ALL_CAN));
        Response response = request.post(RequestURI.PERMISSIONS_URI);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void errorCreatePermissionsWithExistName() throws IOException {
        request.body(permissions.addParamToBodyForCrearePermissijn(PermissionsData.ALL_CAN));
        Response response = request.post(RequestURI.PERMISSIONS_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void getDisplayNameLastPermission() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissions = mapper.readValue(jsonObject.toString(), Permissions.class);
        String displayNameLastPermission = permissions.getModels().get(permissions.getModels().size() - 1).getDisplayName();
        Assert.assertEquals(displayNameLastPermission, PermissionsData.ALL_CAN.getDisplay_name());
    }

    @Test()
    public void getListOfPermissionsAfterCreateNewPermission() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissions = mapper.readValue(jsonObject.toString(), Permissions.class);
        amountPermissionsAfter = permissions.getModels().size();
        idLastPermissions = permissions.getModels().get(amountPermissionsAfter - 1).getId();
        Assert.assertEquals(amountPermissionsBefore + 1, amountPermissionsAfter);
    }

    @Test()
    public void getLastPermissionById() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI + idLastPermissions);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void successUpdatePermission() throws IOException {
        request.body(permissions.addParamToBodyForCrearePermissijn(PermissionsData.TEST_UPDATE_NAME));
        Response response = request.patch(RequestURI.PERMISSIONS_URI + idLastPermissions);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void getDisplayNameLastPermissionAfterUpdate() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissions = mapper.readValue(jsonObject.toString(), Permissions.class);
        String displayNameLastPermission = permissions.getModels().get(permissions.getModels().size() - 1).getDisplayName();
        Assert.assertEquals(displayNameLastPermission, PermissionsData.TEST_UPDATE_NAME.getDisplay_name());
    }

    @Test()
    public void successDeletePermission() throws IOException {
        Response response = request.delete(RequestURI.PERMISSIONS_URI + idLastPermissions);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void getListOfPermissionsAfterDelete() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissions = mapper.readValue(jsonObject.toString(), Permissions.class);
        Assert.assertEquals(permissions.getModels().size(), amountPermissionsBefore);
    }
}
