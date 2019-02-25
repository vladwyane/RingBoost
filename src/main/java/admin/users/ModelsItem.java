package admin.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class ModelsItem{

	@JsonProperty("updated_at")
	private UpdatedAt updatedAt;

	@JsonProperty("name")
	private String name;

	@JsonProperty("created_at")
	private CreatedAt createdAt;

	@JsonProperty("id")
	private int id;

	@JsonProperty("email")
	private String email;

}