package admin.autorization;

import admin.Credentials;
import admin.RequestURI;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Generated;

@Data
@NoArgsConstructor
@Generated("com.robohorse.robopojogenerator")
public class Auth {

	@JsonProperty("success")
	private Success success;

	public static String getToken(Credentials credentials) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String body = "{\n" +
				" \"email\": \"" + credentials.getEmail() + "\",\n" +
				" \"password\": \"" + credentials.getPassword() + "\"\n" +
				"}";

		HttpEntity request = new HttpEntity (body, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Auth> response = restTemplate.exchange(RequestURI.LOGIN_URI, HttpMethod.POST, request, Auth.class);
		return response.getBody().getSuccess().getToken();
	}

}