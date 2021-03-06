package admin.role.currrentRole;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class CreatedAt{

	@JsonProperty("date")
	private String date;

	@JsonProperty("timezone")
	private String timezone;

	@JsonProperty("timezone_type")
	private int timezoneType;

}