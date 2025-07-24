package com.example.hotelmanagement.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class HttpClientUtil {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public HttpClientUtil(RestTemplate restTemplate, @Value("${api.downstream.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public <T, R> ResponseEntity<R> get(String path, T requestBody, Map<String, String> queryParams, Map<String, String> headers, Class<R> responseType) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + path);
        
        // Add query parameters if provided
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        
        HttpHeaders httpHeaders = new HttpHeaders();
        // Add custom headers if provided
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }
        
        HttpEntity<T> entity = new HttpEntity<>(requestBody, httpHeaders);
        
        return restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            responseType
        );
    }

    public <T, R> ResponseEntity<R> post(String path, T requestBody, Map<String, String> headers, Class<R> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        // Add custom headers if provided
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }
        
        HttpEntity<T> entity = new HttpEntity<>(requestBody, httpHeaders);
        
        return restTemplate.exchange(
            baseUrl + path,
            HttpMethod.POST,
            entity,
            responseType
        );
    }

    public <T, R> ResponseEntity<R> put(String path, T requestBody, Map<String, String> headers, Class<R> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        // Add custom headers if provided
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }
        
        HttpEntity<T> entity = new HttpEntity<>(requestBody, httpHeaders);
        
        return restTemplate.exchange(
            baseUrl + path,
            HttpMethod.PUT,
            entity,
            responseType
        );
    }

    public <R> ResponseEntity<R> delete(String path, Map<String, String> queryParams, Map<String, String> headers, Class<R> responseType) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + path);
        
        // Add query parameters if provided
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        
        HttpHeaders httpHeaders = new HttpHeaders();
        // Add custom headers if provided
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }
        
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        
        return restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.DELETE,
            entity,
            responseType
        );
    }
} 