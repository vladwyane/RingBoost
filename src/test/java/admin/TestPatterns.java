package admin;

import admin.categories.Categories;
import admin.categories.currentCategory.CurrentCategory;
import admin.patterns.Patterns;
import admin.patterns.currentPattern.CurrentPattern;
import admin.testData.CategoriesData;
import admin.testData.PatternsData;
import base.TestBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by bigdrop on 2/27/2019.
 */
public class TestPatterns extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    CurrentPattern currentPattern = new CurrentPattern();
    Patterns patterns = new Patterns();
    Categories categories = new Categories();
    CurrentCategory currentCategory = new CurrentCategory();
    int idCreatedCategory;
    int idCreatedPattern;
    int amountPatternsBefore;
    int amountPatternsAfter;


    @BeforeClass()
    public void requestListOfCategoriesAndPatterns() throws IOException {
        request = getToken();

        request.body(currentCategory.addParamToBodyForCreateCategory(CategoriesData.CHRISTMAS));
        Response response = request.post(RequestURI.CATEGORIES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentCategory = mapper.readValue(jsonObject.toString(), CurrentCategory.class);
        idCreatedCategory = currentCategory.getModel().getId();
    }

    @AfterClass()
    public void delete() throws IOException {
        request.delete(RequestURI.CATEGORIES_URI + idCreatedCategory);
    }

    @Test()
    public void getListOfPatterns() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        patterns = mapper.readValue(jsonObject.toString(), Patterns.class);
        amountPatternsBefore = patterns.getModels().size();
        Assert.assertEquals(patterns.getResult(), "success");
    }

    @Test()
    public void errorCreatePatternsWith11Symbols() throws IOException {
        request.body(patterns.addParamToBodyForCrearePatterns(PatternsData.TEST_11_SYMBOLS, idCreatedCategory));
        Response response = request.post(RequestURI.PATTERNS_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void successCreateNewPattern() throws IOException {
        request.body(patterns.addParamToBodyForCrearePatterns(PatternsData.BIG_DROP, idCreatedCategory));
        Response response = request.post(RequestURI.PATTERNS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentPattern = mapper.readValue(jsonObject.toString(), CurrentPattern.class);
        idCreatedPattern = currentPattern.getModel().getId();
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void errorCreatePatternWithExistName() throws IOException {
        request.body(patterns.addParamToBodyForCrearePatterns(PatternsData.BIG_DROP, idCreatedCategory));
        Response response = request.post(RequestURI.PATTERNS_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void checkAmountPatternsAfterCreateNewRole() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        patterns = mapper.readValue(jsonObject.toString(), Patterns.class);
        amountPatternsAfter = patterns.getModels().size();
        Assert.assertEquals(amountPatternsBefore + 1, amountPatternsAfter);
    }

    @Test()
    public void getCurrentPatternById() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI + idCreatedPattern);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void successUpdateCurrentPattern() throws IOException {
        request.body(patterns.addParamToBodyForUpdatePatterns(PatternsData.UPDATE_PRICE, idCreatedCategory));
        Response response = request.patch(RequestURI.PATTERNS_URI + idCreatedPattern);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentPattern = mapper.readValue(jsonObject.toString(), CurrentPattern.class);
        String priceCurrentPattern = currentPattern.getModel().getPrice();
        Assert.assertEquals(priceCurrentPattern, PatternsData.UPDATE_PRICE.getPrice());
    }

    @Test()
    public void successDeletePattern() throws IOException {
        Response response = request.delete(RequestURI.PATTERNS_URI + idCreatedPattern);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void errorPatternByIdAfterDelete() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI + idCreatedPattern);
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test()
    public void getListOfPatternAfterDelete() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        patterns = mapper.readValue(jsonObject.toString(), Patterns.class);
        Assert.assertEquals(patterns.getModels().size(), amountPatternsBefore);
    }
}
