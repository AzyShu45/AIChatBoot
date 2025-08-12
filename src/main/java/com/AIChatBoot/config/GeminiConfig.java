package com.AIChatBoot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Configuration
public class GeminiConfig {

    @Bean
    public RestTemplate geminiRestTemplate(@Value("${gemini.api.key}") String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            URI uri = request.getURI();
            try {
                HttpRequestWrapper wrapper = getHttpRequestWrapper(apiKey, request, uri);

                request.getHeaders().set("Content-Type", "application/json");
                return execution.execute(wrapper, body);
            } catch (URISyntaxException e) {
                throw new RuntimeException("Error while building URI with key", e);
            }
        };

        restTemplate.setInterceptors(List.of(interceptor));
        return restTemplate;
    }

    private static HttpRequestWrapper getHttpRequestWrapper(String apiKey, HttpRequest request, URI uri) throws URISyntaxException {
        URI uriWithKey = new URI(
                uri.getScheme(),
                uri.getAuthority(),
                uri.getPath(),
                (uri.getQuery() == null ? "" : uri.getQuery() + "&") + "key=" + apiKey,
                uri.getFragment()
        );

        return new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return uriWithKey;
            }
        };
    }
}