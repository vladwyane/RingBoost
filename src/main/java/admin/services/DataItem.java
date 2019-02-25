package admin.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@JsonProperty("date_last_parse")
	private String dateLastParse;

	@JsonProperty("has_tollfree")
	private boolean hasTollfree;

	@JsonProperty("is_active")
	private boolean isActive;

	@JsonProperty("name")
	private String name;

	@JsonProperty("number_count")
	private int numberCount;

	@JsonProperty("has_local")
	private boolean hasLocal;

	@JsonProperty("date_check")
	private String dateCheck;

}