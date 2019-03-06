package admin.patterns.currentPattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class CurrentPattern {

	@JsonProperty("result")
	private String result;

	@JsonProperty("model")
	private Model model;

}