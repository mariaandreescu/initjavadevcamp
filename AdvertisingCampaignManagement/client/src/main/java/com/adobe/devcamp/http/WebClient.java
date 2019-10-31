package com.adobe.devcamp;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;

public class WebClient {
    private static final String SERVER = "http://localhost:8181";
    private final RestTemplate restTemplate;

    public WebClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getUsers(String uri) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        final HttpResponse httpResponse = restTemplate.exchange(SERVER + uri, HttpMethod.GET,
                httpEntity, User[].class).getBody();
        return Arrays.asList(httpResponse.getBody());
    }

    public List<User> getUsersByGender(String uri) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        final HttpResponse httpResponse = restTemplate.exchange(SERVER + uri, HttpMethod.GET,
                httpEntity, User[].class).getBody();
        return Arrays.asList(httpResponse.getBody());
    }
}
