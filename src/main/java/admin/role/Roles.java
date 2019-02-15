package admin.role;

import java.util.List;

import admin.testData.RolesData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Roles {

	@JsonProperty("result")
	private String result;

	@JsonProperty("models")
	private List<ModelsItem> models;

	public String addParamToBodyForCreareRole(RolesData rolesData) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("name", rolesData.getName());
		requestBody.put("description", rolesData.getDescription());
		requestBody.put("display_name", rolesData.getDisplay_name());
		return requestBody.toString();
	}
}