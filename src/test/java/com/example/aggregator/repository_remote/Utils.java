package com.example.aggregator.repository_remote;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import reactor.core.publisher.Flux;

class Utils {

    static WebClient.Builder mockWebClientBuilder(
            String expectedBaseUrl, WebClient webClientToReturn) {
        WebClient.Builder webClientBuilder = mock(WebClient.Builder.class);
        when(webClientBuilder.baseUrl(expectedBaseUrl)).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClientToReturn);
        return webClientBuilder;
    }

    /**
     * @param <T>
     *            type of the to-be-returned elements
     * @param uri
     * @param requestMethod
     * @param acceptableMediaType
     * @param headerKey
     * @param headerVal
     * @param responsePayloads
     * @return
     */
    static <T> WebClient mockWebClient(
            String uri, RequestMethod requestMethod, MediaType acceptableMediaType,
            String headerKey, String headerVal, Stream<T> responsePayloads) {

        WebClient webClient = mock(WebClient.class);
        RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);

        switch (requestMethod) {

            case POST:
                RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
                when(webClient.post()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(uri)).thenReturn(requestBodySpec);
                break;

            case GET:
                @SuppressWarnings("rawtypes")
                RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(uri)).thenReturn(requestBodySpec);
                break;

            default:
                throw new UnsupportedOperationException("Not yet implemented...");
        }

        when(requestBodySpec.header(headerKey, headerVal)).thenReturn(requestBodySpec);
        when(requestBodySpec.accept(acceptableMediaType)).thenReturn(requestBodySpec);

        ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(ArgumentMatchers.<Class<T>> notNull())).thenReturn(Flux.fromStream(responsePayloads));

        return webClient;
    }
}
