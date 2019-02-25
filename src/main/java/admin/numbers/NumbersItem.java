package admin.numbers;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class NumbersItem{

	@JsonProperty("country")
	private String country;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("price")
	private int price;

	@JsonProperty("default_price")
	private int defaultPrice;

	@JsonProperty("patterns")
	private List<Object> patterns;

	@JsonProperty("api")
	private List<String> api;

	@JsonProperty("type")
	private String type;

}