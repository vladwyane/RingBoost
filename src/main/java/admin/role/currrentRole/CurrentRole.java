package admin.role.currrentRole;

import admin.testData.RolesData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class CurrentRole {

	@JsonProperty("result")
	private String result;

	@JsonProperty("model")
	private Model model;

	public String addParamToBodyForCreareRole(RolesData rolesData) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("name", rolesData.getName());
		requestBody.put("description", rolesData.getDescription());
		requestBody.put("display_name", rolesData.getDisplay_name());
		return requestBody.toString();
	}

}