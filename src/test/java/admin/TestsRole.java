package admin;

import admin.role.Roles;
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
    int idLastRole;
    int amountRolesBefore;
    int amountRolesAfter;

    @BeforeClass(description = "POST request for getting token")
    public void requestListOfRoles() throws IOException {
        request = getToken();
        Response response = request.get(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        idLastRole = roles.getModels().get(roles.getModels().size() - 1).getId();
        amountRolesBefore = roles.getModels().size();
    }

    @Test()
    public void getListOfRoles() throws IOException {
        Assert.assertEquals(roles.getResult(), "success");
    }

    @Test()
    public void errorCreateRoleWith4Symbols() throws IOException {
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_4_SYMBOLS));
        Response response = request.post(RequestURI.ROLES_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void successCreateRole() throws IOException {
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_5_SYMBOLS));
        Response response = request.post(RequestURI.ROLES_URI);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void errorCreateRoleWithExistName() throws IOException {
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_5_SYMBOLS));
        Response response = request.post(RequestURI.ROLES_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void getDisplayNameLastRole() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        String displayNameLastRole = roles.getModels().get(roles.getModels().size() - 1).getDisplayName();
        Assert.assertEquals(displayNameLastRole, RolesData.TEST_5_SYMBOLS.getDisplay_name());
    }

    @Test()
    public void getListOfRolesAfterCreateNewRole() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        amountRolesAfter = roles.getModels().size();
        idLastRole = roles.getModels().get(amountRolesAfter - 1).getId();
        Assert.assertEquals(amountRolesBefore + 1, amountRolesAfter);
    }

    @Test()
    public void getLastRoleById() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI + idLastRole);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void successUpdateRole() throws IOException {
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_UPDATE_NAME));
        Response response = request.patch(RequestURI.ROLES_URI + idLastRole);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void getDisplayNameLastRoleAfterUpdate() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        String displayNameLastRole = roles.getModels().get(roles.getModels().size() - 1).getDisplayName();
        Assert.assertEquals(displayNameLastRole, RolesData.TEST_UPDATE_NAME.getDisplay_name());
    }

    @Test()
    public void successDeleteRole() throws IOException {
        Response response = request.delete(RequestURI.ROLES_URI + idLastRole);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void getListOfRolesAfterDelete() throws IOException {
        Response response = request.get(RequestURI.ROLES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        Assert.assertEquals(roles.getModels().size(), amountRolesBefore);
    }
}
