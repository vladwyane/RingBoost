package admin.numbers;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Numbers {

	@JsonProperty("pagination")
	private Pagination pagination;

	@JsonProperty("numbers")
	private List<NumbersItem> numbers;

	public boolean resulAfterSearchNumber(String digits) {
		boolean result = false;
		for (int i = 0; i < numbers.size() - 1; i++) {
			if (numbers.get(i).getPhone().contains(digits)) {
				result = true;
			}
			else {
				return false;
			}
		}
		return result;
	}

	public boolean resulAfterFilterationByApiName(String api) {
		boolean result = false;
		for (int i = 0; i < numbers.size() - 1; i++) {
			if (numbers.get(i).getApi().contains(api)) {
				result = true;
			}
			else {
				return false;
			}
		}
		return result;
	}

	public boolean resulAfterFilterationByCountry(String country) {
		boolean result = false;
		for (int i = 0; i < numbers.size() - 1; i++) {
			if (numbers.get(i).getCountry().equals(country)) {
				result = true;
			}
			else {
				return false;
			}
		}
		return result;
	}

	public boolean resulAfterFilterationByTypeNumbers(String type) {
		boolean result = false;
		for (int i = 0; i < numbers.size() - 1; i++) {
			if (numbers.get(i).getType().equals(type)) {
				result = true;
			}
			else {
				return false;
			}
		}
		return result;
	}

	public boolean resulAfterSearchNumberByPrice(String price) {
		boolean result = false;
		for (int i = 0; i < numbers.size() - 1; i++) {
			if (String.valueOf(numbers.get(i).getPrice()).contains(price)) {
				result = true;
			}
			else {
				return false;
			}
		}
		return result;
	}

	public boolean resulAfterSearchNumberByDefaultPrice(String defaultPrice) {
		boolean result = false;
		for (int i = 0; i < numbers.size() - 1; i++) {
			if (String.valueOf(numbers.get(i).getPrice()).contains(defaultPrice)) {
				result = true;
			}
			else {
				return false;
			}
		}
		return result;
	}


}