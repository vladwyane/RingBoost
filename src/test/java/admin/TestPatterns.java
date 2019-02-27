package admin;

import admin.categories.Categories;
import admin.patterns.Patterns;
import admin.permissionToRole.PermissionToRole;
import admin.permissions.Permissions;
import admin.role.Roles;
import admin.testData.CategoriesData;
import admin.testData.PatternsData;
import admin.testData.PermissionsData;
import admin.testData.RolesData;
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
import sun.security.krb5.internal.PAData;

import java.io.IOException;

/**
 * Created by bigdrop on 2/27/2019.
 */
public class TestPatterns extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Patterns patterns = new Patterns();
    Categories categories = new Categories();
    int idLastCategory;
    int idLastPattern;
    int amountPatternsBefore;
    int amountPatternsAfter;


    @BeforeClass()
    public void requestListOfCategoriesAndPatterns() throws IOException {
        request = getToken();

        request.body(categories.addParamToBodyForCreateCategory(CategoriesData.CHRISTMAS));
        request.post(RequestURI.CATEGORIES_URI);

        Response response = request.get(RequestURI.PATTERNS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        patterns = mapper.readValue(jsonObject.toString(), Patterns.class);
        idLastPattern = patterns.getModels().get(patterns.getModels().size() - 1).getId();
        amountPatternsBefore = patterns.getModels().size();
        response = request.get(RequestURI.CATEGORIES_URI);
        jsonObject = new JSONObject(response.asString());
        categories = mapper.readValue(jsonObject.toString(), Categories.class);
        idLastCategory = categories.getModels().get(categories.getModels().size() - 1).getId();

    }

    @AfterClass()
    public void delete() throws IOException {
        request.delete(RequestURI.CATEGORIES_URI + idLastCategory);
    }

    @Test()
    public void getListOfPatterns() throws IOException {
        Assert.assertEquals(patterns.getResult(), "success");
    }

    @Test()
    public void errorCreatePatternsWith11Symbols() throws IOException {
        request.body(patterns.addParamToBodyForCrearePatterns(PatternsData.TEST_11_SYMBOLS, idLastCategory));
        Response response = request.post(RequestURI.PATTERNS_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void successCreatePattern() throws IOException {
        request.body(patterns.addParamToBodyForCrearePatterns(PatternsData.BIG_DROP, idLastCategory));
        Response response = request.post(RequestURI.PATTERNS_URI);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void errorCreatePatternWithExistName() throws IOException {
        request.body(patterns.addParamToBodyForCrearePatterns(PatternsData.BIG_DROP, idLastCategory));
        Response response = request.post(RequestURI.PATTERNS_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void getNameLastPattern() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        patterns = mapper.readValue(jsonObject.toString(), Patterns.class);
        String nameLastPattern = patterns.getModels().get(patterns.getModels().size() - 1).getPattern();
        Assert.assertEquals(nameLastPattern, PatternsData.BIG_DROP.getPattern());
    }

    @Test()
    public void getListOfPatternsAfterCreateNewRole() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        patterns = mapper.readValue(jsonObject.toString(), Patterns.class);
        amountPatternsAfter = patterns.getModels().size();
        idLastPattern = patterns.getModels().get(amountPatternsAfter - 1).getId();
        Assert.assertEquals(amountPatternsBefore + 1, amountPatternsAfter);
    }

    @Test()
    public void getLastPatternById() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI + idLastPattern);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void successUpdatePattern() throws IOException {
        request.body(patterns.addParamToBodyForCrearePatterns(PatternsData.UPDATE_PRICE, idLastCategory));
        Response response = request.patch(RequestURI.PATTERNS_URI + idLastPattern);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void getDisplayNameLastPatternAfterUpdate() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        patterns = mapper.readValue(jsonObject.toString(), Patterns.class);
        int priceLastPattern = patterns.getModels().get(patterns.getModels().size() - 1).getPrice();
        Assert.assertEquals(priceLastPattern, PatternsData.UPDATE_PRICE.getPattern());
    }

    @Test()
    public void successDeletePattern() throws IOException {
        Response response = request.delete(RequestURI.PATTERNS_URI + idLastPattern);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void getListOfPatternAfterDelete() throws IOException {
        Response response = request.get(RequestURI.PATTERNS_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        patterns = mapper.readValue(jsonObject.toString(), Patterns.class);
        Assert.assertEquals(patterns.getModels().size(), amountPatternsBefore);
    }
}
