package com.epam.ws1;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

public class testGetMethodByRestTemplate {
	@Test
	public void testGetMethod() {
		RestTemplate restTemplate = new RestTemplate();
		String bookResourceUrl = "http://localhost:8080/book";
		ResponseEntity<String> response = restTemplate.getForEntity(bookResourceUrl, String.class);
		int statusCode = response.getStatusCodeValue();
		HttpStatus statusCodeEntity = response.getStatusCode();
		statusCodeEntity.value();
		statusCodeEntity.getReasonPhrase();
		statusCodeEntity.is1xxInformational();
		statusCodeEntity.is2xxSuccessful();
		statusCodeEntity.is3xxRedirection();
		statusCodeEntity.is4xxClientError();
		statusCodeEntity.is5xxServerError();
		HttpHeaders headers = response.getHeaders();
		List<String> headersValue = headers.get("HEADER_NAME");
		headers.getContentType();
		headers.getContentLength();
		headers.getLocation();
		String rpBody = response.getBody();
		response.hasBody();
		
	}
}
