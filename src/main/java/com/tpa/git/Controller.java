package com.tpa.git;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tpa.git.dto.GitResponse;

import lombok.extern.log4j.Log4j2;
@org.springframework.stereotype.Controller
@Log4j2
public class Controller {

	@Value("${token}")
	private String token;

	@Value("${github-user}")
	private String user;

	/* http://www.jsonschema2pojo.org/ */

	@RequestMapping(path = "/create/{newRepository}", method = RequestMethod.GET)
	public void readBranches1(@PathVariable String newRepository) throws Exception {
		
		
		if(token.isBlank())
			throw new Exception("Agregar Token valido");

		String url = "https://api.github.com/user/repos";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", token);

		// request body parameters
		Map<String, Object> map = new HashMap<>();
		map.put("name", newRepository);
		map.put("description", "Created from the API");
		map.put("homepage", "https://github.com");
		map.put("private", true);
		map.put("has_issues", true);
		map.put("has_projects", true);
		map.put("has_wiki", true);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<GitResponse> response = restTemplate.postForEntity(url, entity, GitResponse.class);

		log.info(response);

		if (response.getStatusCode() == HttpStatus.CREATED) {
			System.out.println("Request Successful");
			System.out.println(response.getBody());
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}
	}

	@RequestMapping(path = "/delete/{existingRepo}", method = RequestMethod.GET)
	public void delete(@PathVariable String existingRepo) throws URISyntaxException {

		String url = "https://api.github.com/repos/" + user + "/" + existingRepo;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", token);

		// request body parameters
		Map<String, Object> map = new HashMap<>();

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		try {
			// Send request to the GitLab-API
			restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
		} catch (RestClientException r) {
			r.printStackTrace();
		}

	}

}
