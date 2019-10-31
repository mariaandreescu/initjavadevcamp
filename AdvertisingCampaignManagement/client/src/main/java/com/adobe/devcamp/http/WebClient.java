package com.adobe.devcamp.http;

import com.adobe.devcamp.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;

@Component
public class WebClient {
    private static final String SERVER = "http://localhost:8181";
    private final RestTemplate restTemplate;

    public WebClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User>  getUsers(String uri) {
        final HttpHeaders httpHeaders = getHttpHeaders();
        final HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        final ResponseEntity<User[]> httpResponse = restTemplate.exchange(SERVER + uri,
                HttpMethod.GET, httpEntity, User[].class);

        return asList(httpResponse.getBody());
    }

    public List<User>  getUsersByGender(String uri, String gender) {
        final HttpHeaders httpHeaders = getHttpHeaders();
        final HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        final ResponseEntity<User[]> httpResponse = restTemplate.exchange(SERVER + uri + "?gender="+gender,
                HttpMethod.GET, httpEntity, User[].class);

        return asList(httpResponse.getBody());
    }

    private HttpHeaders getHttpHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
