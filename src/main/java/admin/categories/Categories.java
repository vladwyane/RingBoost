package admin.categories;

import java.util.List;

import admin.testData.CategoriesData;
import admin.testData.RolesData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Categories {

	@JsonProperty("result")
	private String result;

	@JsonProperty("models")
	private List<ModelsItem> models;

	public String addParamToBodyForCreateCategory(CategoriesData categoriesData) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("name", categoriesData.getName());
		requestBody.put("price", categoriesData.getPrice());
		return requestBody.toString();
	}
}