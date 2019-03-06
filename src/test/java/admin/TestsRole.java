package admin;

import admin.role.Roles;
import admin.role.currrentRole.CurrentRole;
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
 * Created by bigdrop on 2/14/2019.
 */
public class TestsRole extends TestBase{

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Roles roles = new Roles();
    CurrentRole currentRole = new CurrentRole();
    int idCreatedRole;
    int amountRolesBefore;
    int amountRolesAfter;

    @BeforeClass(description = "POST request for getting token")
    public void headerOfRequest() throws IOException {
        request = getToken();
    }

    @Test()
    public void getListOfRoles() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        amountRolesBefore = roles.getModels().size();
        Assert.assertEquals(roles.getResult(), "success");
    }

    @Test()
    public void errorCreateRoleWith4Symbols() throws IOException {
        request.body(currentRole.addParamToBodyForCreareRole(RolesData.TEST_4_SYMBOLS));
        Response response = request.post(RequestURI.ROLES_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void successCreateNewRole() throws IOException {
        request.body(currentRole.addParamToBodyForCreareRole(RolesData.TEST_5_SYMBOLS));
        Response response = request.post(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentRole = mapper.readValue(jsonObject.toString(), CurrentRole.class);
        idCreatedRole = currentRole.getModel().getId();
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void errorCreateRoleWithExistName() throws IOException {
        request.body(currentRole.addParamToBodyForCreareRole(RolesData.TEST_5_SYMBOLS));
        Response response = request.post(RequestURI.ROLES_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void checkAmountRolesAfterCreateNewRole() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        amountRolesAfter = roles.getModels().size();
        Assert.assertEquals(amountRolesBefore + 1, amountRolesAfter);
    }

    @Test()
    public void getCurrentRoleById() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI + idCreatedRole);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void successUpdateCurrentRole() throws IOException {
        request.body(currentRole.addParamToBodyForCreareRole(RolesData.TEST_UPDATE_NAME));
        Response response = request.patch(RequestURI.ROLES_URI + idCreatedRole);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentRole = mapper.readValue(jsonObject.toString(), CurrentRole.class);
        String displayNameCurrentRole = currentRole.getModel().getDisplayName();
        Assert.assertEquals(displayNameCurrentRole, RolesData.TEST_UPDATE_NAME.getDisplay_name());
    }

    @Test()
    public void successDeleteRole() throws IOException {
        Response response = request.delete(RequestURI.ROLES_URI + idCreatedRole);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void errorRoleByIdAfterDelete() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI + idCreatedRole);
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test()
    public void getListOfRolesAfterDelete() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        Assert.assertEquals(roles.getModels().size(), amountRolesBefore);
    }
}
