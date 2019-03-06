package admin.patterns;

import java.util.List;

import admin.testData.PatternsData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Patterns {

	@JsonProperty("result")
	private String result;

	@JsonProperty("models")
	private List<ModelsItem> models;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setModels(List<ModelsItem> models){
		this.models = models;
	}

	public List<ModelsItem> getModels(){
		return models;
	}

	public String addParamToBodyForCrearePatterns(PatternsData patternsData, int category_id) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("pattern", patternsData.getPattern());
		requestBody.put("category_type", patternsData.getCategory_type());
		requestBody.put("price", patternsData.getPrice());
		requestBody.put("category_id", category_id);
		return requestBody.toString();
	}

	public String addParamToBodyForUpdatePatterns(PatternsData patternsData, int category_id) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("price", patternsData.getPrice());
		requestBody.put("category_id", category_id);
		return requestBody.toString();
	}


}