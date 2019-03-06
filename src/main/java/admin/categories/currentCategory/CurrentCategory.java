package admin.categories.currentCategory;

import admin.testData.CategoriesData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class CurrentCategory {

	@JsonProperty("result")
	private String result;

	@JsonProperty("model")
	private Model model;

	public String addParamToBodyForCreateCategory(CategoriesData categoriesData) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("name", categoriesData.getName());
		requestBody.put("price", categoriesData.getPrice());
		return requestBody.toString();
	}
}