package admin.users.currentUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Success{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

}