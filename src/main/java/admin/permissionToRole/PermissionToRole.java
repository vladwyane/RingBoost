package admin.permissionToRole;

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
public class PermissionToRole {

	@JsonProperty("result")
	private String result;

	@JsonProperty("permissions")
	private List<PermissionsItem> permissions;

}