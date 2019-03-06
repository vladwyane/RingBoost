package admin.categories.currentCategory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Model{

	@JsonProperty("updated_at")
	private UpdatedAt updatedAt;

	@JsonProperty("price")
	private String price;

	@JsonProperty("name")
	private String name;

	@JsonProperty("created_at")
	private CreatedAt createdAt;

	@JsonProperty("id")
	private int id;

}