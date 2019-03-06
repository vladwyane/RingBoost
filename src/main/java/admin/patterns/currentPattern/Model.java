package admin.patterns.currentPattern;

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

	@JsonProperty("pattern")
	private String pattern;

	@JsonProperty("created_at")
	private CreatedAt createdAt;

	@JsonProperty("id")
	private int id;

	@JsonProperty("type")
	private Object type;

	@JsonProperty("category")
	private Category category;

}