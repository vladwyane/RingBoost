package admin;

import admin.permissions.Permissions;
import admin.services.Services;
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
import java.util.List;

/**
 * Created by bigdrop on 2/25/2019.
 */
public class TestsServices extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Services services = new Services();


    @BeforeClass(description = "POST request for getting token")
    public void requestListOfPermissions() throws IOException {
        request = getToken();
        Response response = request.get(RequestURI.SERVICES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        services = mapper.readValue(jsonObject.toString(), Services.class);

    }

    @Test()
    public void getListOfApi() throws IOException {
        Assert.assertEquals(services.getData().size(), 17);
    }

    @Test()
    public void checkStatusAllApi() throws IOException {
        Assert.assertTrue(services.checkStatusAllApi());
    }

    @Test()
    public void parseOneApiByName() throws IOException {
        List<String> listApiName = services.parseOneApi("west");
        JSONObject requestBody = new JSONObject();
        requestBody.put("names", listApiName);
        request.body(requestBody.toString());
        Response response = request.post(RequestURI.SERVICES_URI);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void parseAllApi() throws IOException {
        List<String> listApiName = services.parseAllActiveApi();
        JSONObject requestBody = new JSONObject();
        requestBody.put("names", listApiName);
        request.body(requestBody.toString());
        Response response = request.post(RequestURI.SERVICES_URI);
        Assert.assertEquals(response.statusCode(), 200);
    }

}
