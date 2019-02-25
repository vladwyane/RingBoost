package admin.permissions;

import java.util.List;

import admin.testData.PermissionsData;
import admin.testData.RolesData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Permissions {

	@JsonProperty("result")
	private String result;

	@JsonProperty("models")
	private List<ModelsItem> models;

	public String addParamToBodyForCrearePermissijn (PermissionsData permissionsData) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("name", permissionsData.getName());
		requestBody.put("description", permissionsData.getDescription());
		requestBody.put("display_name", permissionsData.getDisplay_name());
		return requestBody.toString();
	}

}