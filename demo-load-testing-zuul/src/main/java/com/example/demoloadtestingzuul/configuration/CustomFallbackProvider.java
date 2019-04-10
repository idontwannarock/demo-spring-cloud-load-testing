package com.example.demoloadtestingzuul.configuration;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Configuration
public class CustomFallbackProvider implements FallbackProvider {

    @Override
    public String getRoute() {
        // States providing fallback for which service, and * means all.
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause instanceof HystrixTimeoutException) {
            return response(GATEWAY_TIMEOUT);
        } else {
            return response(INTERNAL_SERVER_ERROR);
        }
    }

    private ClientHttpResponse response(final HttpStatus status) {
        return new ClientHttpResponse() {

            @Override
            public HttpStatus getStatusCode() {
                return status;
            }

            @Override
            public int getRawStatusCode() {
                return status.value();
            }

            @Override
            public String getStatusText() {
                return status.getReasonPhrase();
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream("Service unavailable, please try again later.".getBytes());
            }

            @Override
            public void close() {}
        };
    }
}
