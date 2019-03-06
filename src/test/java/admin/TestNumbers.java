package admin;

import admin.numbers.Numbers;
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
 * Created by bigdrop on 2/25/2019.
 */
public class TestNumbers extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Numbers numbers = new Numbers();

    @BeforeClass(description = "POST request for getting token")
    public void requestListOfPermissions() throws IOException {
        request = getToken();
        Response response = request.get(RequestURI.NUMBERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        numbers = mapper.readValue(jsonObject.toString(), Numbers.class);
    }

    @Test()
    public void getListOfNumbers() throws IOException {
        Assert.assertTrue(numbers.getPagination().getTotalCount() != 0);
    }

    @Test()
    public void searchNumbersByWord() throws IOException {
        RequestSpecification request;
        request = getToken();
        request.param("phone", "vlad");
        Response response = request.get(RequestURI.NUMBERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        numbers = mapper.readValue(jsonObject.toString(), Numbers.class);
        boolean result = numbers.resulAfterSearchNumber("8523");
        Assert.assertTrue(result);
    }

    @Test()
    public void searchNumbersByDigits() throws IOException {
        RequestSpecification request;
        request = getToken();
        request.param("phone", "92345");
        Response response = request.get(RequestURI.NUMBERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        numbers = mapper.readValue(jsonObject.toString(), Numbers.class);
        boolean result = numbers.resulAfterSearchNumber("92345");
        Assert.assertTrue(result);
    }

    @Test()
    public void filtrationNumbersByApi() throws IOException {
        RequestSpecification request;
        request = getToken();
        request.param("api", "west");
        Response response = request.get(RequestURI.NUMBERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        numbers = mapper.readValue(jsonObject.toString(), Numbers.class);
        boolean result = numbers.resulAfterFilterationByApiName("west");
        Assert.assertTrue(result);
    }

    @Test()
    public void filtrationNumbersByCountry() throws IOException {
        RequestSpecification request;
        request = getToken();
        request.formParam("country", "ca");
        Response response = request.get(RequestURI.NUMBERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        numbers = mapper.readValue(jsonObject.toString(), Numbers.class);
        boolean result = numbers.resulAfterFilterationByCountry("ca");
        Assert.assertTrue(result);
    }

    @Test()
    public void filtrationNumbersByTypeNumbers() throws IOException {
        RequestSpecification request;
        request = getToken();
        request.formParam("type", "tollfree");
        Response response = request.get(RequestURI.NUMBERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        numbers = mapper.readValue(jsonObject.toString(), Numbers.class);
        boolean result = numbers.resulAfterFilterationByTypeNumbers("tollfree");
        Assert.assertTrue(result);
    }

    @Test()
    public void searchNumbersByPrise() throws IOException {
        RequestSpecification request;
        request = getToken();
        request.formParam("price", "0");
        Response response = request.get(RequestURI.NUMBERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        numbers = mapper.readValue(jsonObject.toString(), Numbers.class);
        boolean result = numbers.resulAfterSearchNumberByPrice("0");
        Assert.assertTrue(result);
    }

    @Test()
    public void searchNumbersByDefaultPrise() throws IOException {
        RequestSpecification request;
        request = getToken();
        request.formParam("default_price", "0");
        Response response = request.get(RequestURI.NUMBERS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        numbers = mapper.readValue(jsonObject.toString(), Numbers.class);
        boolean result = numbers.resulAfterSearchNumberByDefaultPrice("0");
        Assert.assertTrue(result);
    }
}
