package admin.users;

import java.util.List;

import admin.testData.RolesData;
import admin.testData.UsersData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Users {

	@JsonProperty("result")
	private String result;

	@JsonProperty("models")
	private List<ModelsItem> models;

	public int getElementOrderByUserId (int userId) {
		int elementOrder = 0;
		for (int i = 0; i < models.size(); i++) {
			if (models.get(i).getId() == userId) {
				elementOrder = i;
				return elementOrder;
			}
		}
		return elementOrder;
	}

}