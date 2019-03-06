package admin.users.currentUser;

import admin.testData.UsersData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class CurrentUser {

	@JsonProperty("success")
	private Success success;

	public String addParamToBodyForCreareUser(UsersData usersData) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("email", usersData.getEmail());
		requestBody.put("name", usersData.getName());
		requestBody.put("password", usersData.getPassword());
		requestBody.put("c_password", usersData.getC_password());
		return requestBody.toString();
	}

	public String addParamToBodyForUpdateUser(UsersData usersData) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("name", usersData.getName());
		requestBody.put("email", usersData.getEmail());
		return requestBody.toString();
	}

}