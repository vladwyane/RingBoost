package admin.roleToUser;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class RoleToUser {

	@JsonProperty("result")
	private String result;

	@JsonProperty("roles")
	private List<RolesItem> roles;

}