package admin.roleToUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class UpdatedAt{

	@JsonProperty("date")
	private String date;

	@JsonProperty("timezone")
	private String timezone;

	@JsonProperty("timezone_type")
	private int timezoneType;

}