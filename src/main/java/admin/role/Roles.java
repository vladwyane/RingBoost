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

}