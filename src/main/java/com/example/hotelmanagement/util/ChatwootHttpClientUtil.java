package com.example.hotelmanagement.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class ChatwootHttpClientUtil {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiAccessToken;
    private final String apiPlatformAccessToken;

    @Autowired
    public ChatwootHttpClientUtil(RestTemplate restTemplate,
                                  @Value("${api.chatwoot.baseUrl}") String baseUrl,
                                  @Value("${api.chatwoot.administrator.access.token}") String apiAccessToken,
                                  @Value("${api.chatwoot.platform.access.token}") String apiPlatformAccessToken) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiAccessToken = apiAccessToken;
        this.apiPlatformAccessToken = apiPlatformAccessToken;
    }

    public <T, R> ResponseEntity<R> get(String path, T requestBody, Map<String, String> queryParams, Class<R> responseType, String accessToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + path);
        
        // Add query parameters if provided
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        
        HttpHeaders httpHeaders = createHeaders(accessToken);
        
        HttpEntity<T> entity = new HttpEntity<>(requestBody, httpHeaders);
        
        return restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            responseType
        );
    }

    public <T, R> ResponseEntity<R> post(String path, T requestBody, Class<R> responseType, String accessToken) {
        HttpHeaders httpHeaders = createHeaders(accessToken);
        
        HttpEntity<T> entity = new HttpEntity<>(requestBody, httpHeaders);
        
        return restTemplate.exchange(
            baseUrl + path,
            HttpMethod.POST,
            entity,
            responseType
        );
    }

    public <T, R> ResponseEntity<R> put(String path, T requestBody, Class<R> responseType, String accessToken) {
        HttpHeaders httpHeaders = createHeaders(accessToken);
        
        HttpEntity<T> entity = new HttpEntity<>(requestBody, httpHeaders);
        
        return restTemplate.exchange(
            baseUrl + path,
            HttpMethod.PUT,
            entity,
            responseType
        );
    }

    public <T, R> ResponseEntity<R> patch(String path, T requestBody, Class<R> responseType, String accessToken) {
        HttpHeaders httpHeaders = createHeaders(accessToken);
        
        HttpEntity<T> entity = new HttpEntity<>(requestBody, httpHeaders);
        
        return restTemplate.exchange(
            baseUrl + path,
            HttpMethod.PATCH,
            entity,
            responseType
        );
    }

    public <R> ResponseEntity<R> delete(String path, Map<String, String> queryParams, Class<R> responseType, String accessToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + path);
        
        // Add query parameters if provided
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        
        HttpHeaders httpHeaders = createHeaders(accessToken);
        
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        
        return restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.DELETE,
            entity,
            responseType
        );
    }

    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        
        // Add Chatwoot API access token if configured
        if (StringUtils.hasText(accessToken)) {
            httpHeaders.set("api_access_token", accessToken);
        }
        return httpHeaders;
    }
} 