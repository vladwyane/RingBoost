package admin;

import admin.categories.Categories;
import admin.categories.currentCategory.CurrentCategory;
import admin.role.Roles;
import admin.testData.CategoriesData;
import admin.testData.RolesData;
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
 * Created by bigdrop on 2/26/2019.
 */
public class TestsCategories extends TestBase {

    RequestSpecification request = RestAssured.given();
    ObjectMapper mapper = new ObjectMapper();
    Categories categories = new Categories();
    CurrentCategory currentCategory = new CurrentCategory();
    int idCreatedCategory;
    int amountCategoryBefore;
    int amountCategoryAfter;

    @BeforeClass(description = "POST request for getting token")
    public void headerOfRequest() throws IOException {
        request = getToken();
    }

    @Test()
    public void getListOfCategories() throws IOException {
        Response response = request.get(RequestURI.CATEGORIES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        categories = mapper.readValue(jsonObject.toString(), Categories.class);
        amountCategoryBefore = categories.getModels().size();
        Assert.assertEquals(categories.getResult(), "success");
    }

    @Test()
    public void successCreateNewCategory() throws IOException {
        request.body(currentCategory.addParamToBodyForCreateCategory(CategoriesData.CHRISTMAS));
        Response response = request.post(RequestURI.CATEGORIES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentCategory = mapper.readValue(jsonObject.toString(), CurrentCategory.class);
        idCreatedCategory = currentCategory.getModel().getId();
        Assert.assertEquals(currentCategory.getModel().getName(), CategoriesData.CHRISTMAS.getName());
    }

    @Test()
    public void errorCreateCategoryWithExistName() throws IOException {
        request.body(currentCategory.addParamToBodyForCreateCategory(CategoriesData.CHRISTMAS));
        Response response = request.post(RequestURI.CATEGORIES_URI);
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test()
    public void checkAmountCategoriesAfterCreateNewCategory() throws IOException {
        Response response = request.get(RequestURI.CATEGORIES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        categories = mapper.readValue(jsonObject.toString(), Categories.class);
        amountCategoryAfter = categories.getModels().size();
        Assert.assertEquals(amountCategoryBefore + 1, amountCategoryAfter);
    }

    @Test()
    public void getCurrentCategoryById() throws IOException {
        Response response = request.get(RequestURI.CATEGORIES_URI + idCreatedCategory);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test()
    public void successUpdateCurrentCategory() throws IOException {
        request.body(currentCategory.addParamToBodyForCreateCategory(CategoriesData.CATEGORY_UPDATE));
        Response response = request.patch(RequestURI.CATEGORIES_URI + idCreatedCategory);
        JSONObject jsonObject = new JSONObject(response.asString());
        currentCategory = mapper.readValue(jsonObject.toString(), CurrentCategory.class);
        String nameCurrentCutegory = currentCategory.getModel().getName();
        Assert.assertEquals(nameCurrentCutegory, CategoriesData.CATEGORY_UPDATE.getName());
    }

    @Test()
    public void successDeleteCategory() throws IOException {
        Response response = request.delete(RequestURI.CATEGORIES_URI + idCreatedCategory);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test()
    public void errorCategoryByIdAfterDelete() throws IOException {
        Response response = request.get(RequestURI.CATEGORIES_URI + idCreatedCategory);
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test()
    public void getListOfCategoriesAfterDelete() throws IOException {
        Response response = request.get(RequestURI.CATEGORIES_URI);
        JSONObject jsonObject = new JSONObject(response.asString());
        categories = mapper.readValue(jsonObject.toString(), Categories.class);
        Assert.assertEquals(categories.getModels().size(), amountCategoryBefore);
    }
}
