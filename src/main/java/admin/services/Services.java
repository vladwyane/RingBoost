package admin.services;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Services {

	@JsonProperty("data")
	private List<DataItem> data;

	public boolean checkStatusAllApi() {
		boolean result = false;
		for (int i = 0; i < data.size() - 1; i++) {
			if (data.get(i).isActive() == true) {
				result = true;
			}
			else {
				return false;
			}
		}
		return result;
	}

	public List<String> parseOneApi(String nameApi) {
		List<String> listApiname = new ArrayList<String>();
		listApiname.add(nameApi);
		return listApiname;
	}

	public List<String> parseAllActiveApi() {
		List<String> listApiname = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).isActive() == true)
				listApiname.add(data.get(i).getName());
		}
		return listApiname;
	}


}