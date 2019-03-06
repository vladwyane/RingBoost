package admin;

import admin.permissions.Permissions;
import admin.permissions.currentPermission.CurrentPermission;
import admin.testData.PermissionsData;
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
public class TestsPermission extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Permissions permissions = new Permissions();
    CurrentPermission currentPermission = new CurrentPermission();
    int idCreatedPermissions;
    int amountPermissionsBefore;
    int amountPermissionsAfter;


    @BeforeClass(description = "POST request for getting token")
    public void requestListOfPermissions() throws IOException {
        request = getToken();
    }

    @Test()
    public void getListOfPermissions() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissions = mapper.readValue(jsonObject.toString(), Permissions.class);
        amountPermissionsBefore = permissions.getModels().size();
        Assert.assertEquals(permissions.getResult(), "success");
    }

    @Test()
    public void successCreateNewPermission() throws IOException {
        request.body(currentPermission.addParamToBodyForCrearePermission(PermissionsData.ALL_CAN));
        Response response = request.post(RequestURI.PERMISSIONS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentPermission = mapper.readValue(jsonObject.toString(), CurrentPermission.class);
        idCreatedPermissions = currentPermission.getModel().getId();
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void errorCreatePermissionsWithExistName() throws IOException {
        request.body(currentPermission.addParamToBodyForCrearePermission(PermissionsData.ALL_CAN));
        Response response = request.post(RequestURI.PERMISSIONS_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void checkAmountPermissionsAfterCreateNewPermission() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissions = mapper.readValue(jsonObject.toString(), Permissions.class);
        amountPermissionsAfter = permissions.getModels().size();
        Assert.assertEquals(amountPermissionsBefore + 1, amountPermissionsAfter);
    }

    @Test()
    public void getCurrentPermissionById() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI + idCreatedPermissions);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void successUpdatePermission() throws IOException {
        request.body(currentPermission.addParamToBodyForCrearePermission(PermissionsData.TEST_UPDATE_NAME));
        Response response = request.patch(RequestURI.PERMISSIONS_URI + idCreatedPermissions);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentPermission = mapper.readValue(jsonObject.toString(), CurrentPermission.class);
        String displayNameCurrentPermission = currentPermission.getModel().getDisplayName();
        Assert.assertEquals(displayNameCurrentPermission, PermissionsData.TEST_UPDATE_NAME.getDisplay_name());
    }

    @Test()
    public void successDeletePermission() throws IOException {
        Response response = request.delete(RequestURI.PERMISSIONS_URI + idCreatedPermissions);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void errorPermissionByIdAfterDelete() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI + idCreatedPermissions);
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test()
    public void getListOfPermissionsAfterDelete() throws IOException {
        Response response = request.get(RequestURI.PERMISSIONS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        permissions = mapper.readValue(jsonObject.toString(), Permissions.class);
        Assert.assertEquals(permissions.getModels().size(), amountPermissionsBefore);
    }
}
