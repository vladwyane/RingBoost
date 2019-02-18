package admin;

import admin.autorization.Auth;
import admin.role.Roles;
import admin.testData.RolesData;
import base.TestBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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


    @BeforeTest(description = "POST request for getting token")
    public void getToken() {
        String token = Auth.getToken(Credentials.ADMIN);
        request = authWithToken(token);
        request.header("Content-Type", "application/json");
    }

    @Test()
    public void test1getListOfRoles() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        Roles roles = mapper.readValue(jsonObject.toString(), Roles.class);
        Assert.assertEquals(roles.getResult(), "success");
    }

    @Test()
    public void test2errorCreateRoleWith4Symbols() throws IOException {
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_4_SYMBOLS));
        Response response = request.post(RequestURI.ROLE_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void test3successCreateRole() throws IOException {
        amountRolesBefore = roles.getModels().size();
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_5_SYMBOLS));
        Response response = request.post(RequestURI.ROLE_URI);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void test4errorCreateRoleWithExistName() throws IOException {
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_5_SYMBOLS));
        Response response = request.post(RequestURI.ROLE_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }


    @Test()
    public void test4getDisplayNameLastRole() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        String displayNameLastRole = roles.getModels().get(roles.getModels().size() - 1).getDisplayName();
        Assert.assertEquals(displayNameLastRole, RolesData.TEST_5_SYMBOLS.getDisplay_name());
    }

    @Test()
    public void test4getListOfRolesAfterCreateNewRole() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        amountRolesAfter = roles.getModels().size();
        idLastRole = roles.getModels().get(amountRolesAfter - 1).getId();
        Assert.assertEquals(amountRolesBefore + 1, amountRolesAfter);
    }

    @Test()
    public void test5getLastRoleById() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI + idLastRole);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void test6successUpdateRole() throws IOException {
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_UPDATE_NAME));
        Response response = request.patch(RequestURI.ROLE_URI + idLastRole);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void test7getDisplayNameLastRoleAfterUpdate() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        String displayNameLastRole = roles.getModels().get(roles.getModels().size() - 1).getDisplayName();
        Assert.assertEquals(displayNameLastRole, RolesData.TEST_UPDATE_NAME.getDisplay_name());
    }

    @Test()
    public void test8successDeleteRole() throws IOException {
        Response response = request.delete(RequestURI.ROLE_URI + 35);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void test9getListOfRolesAfterDelete() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        Assert.assertEquals(roles.getModels().size(), amountRolesBefore);
    }
}
