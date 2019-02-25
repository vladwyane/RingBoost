package admin.numbers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Pagination{

	@JsonProperty("per_page")
	private int perPage;

	@JsonProperty("total_count")
	private int totalCount;

	@JsonProperty("page")
	private int page;

	@JsonProperty("page_count")
	private int pageCount;

}