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
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
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

    @BeforeTest(description = "POST request for getting token")
    public void getToken() {
        String token = Auth.getToken(Credentials.ADMIN);
        request = authWithToken(token);
        request.header("Content-Type", "application/json");
    }

    @Test()
    public void getListOfRoles() throws IOException {
        Response response = request.get(RequestURI.ROLE_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        Assert.assertEquals(roles.getResult(), "success");
    }

    @Test()
    public void errorCreateRoleWith4Symbols() throws IOException {
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_4_SYMBOLS));
        Response response = request.post(RequestURI.ROLE_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void successCreateRole() throws IOException {
        int accountBefore = roles.getModels().size();
        request.body(roles.addParamToBodyForCreareRole(RolesData.TEST_5_SYMBOLS));
        Response response = request.post(RequestURI.ROLE_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        roles = mapper.readValue(jsonObject.toString(), Roles.class);
        int accountAefore = roles.getModels().size();
    }

    @Test()
    public void createRole(){
        request.header("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();

        String token = Auth.getToken(Credentials.ADMIN);
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + token);
        String body = "{\n" +
                " \"name\": \"" + "Vlad" + "\",\n" +
                " \"description\": \"" + "QA" + "\"\n" +
                " \"display_name\": \"" + "VladQA" + "\"\n" +
                "}";

        HttpEntity request = new HttpEntity (body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Roles> response = restTemplate.exchange(RequestURI.ROLE_URI, HttpMethod.POST, request, Roles.class);
        response.getBody().getResult();
    }
}
