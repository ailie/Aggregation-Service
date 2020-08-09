package com.example.aggregator.repository_remote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import static com.example.aggregator.repository_remote.Utils.mockWebClient;
import static com.example.aggregator.repository_remote.Utils.mockWebClientBuilder;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.aggregator.repository_remote.RemoteRepositoryAuthenticator.LoginResponse;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class RemoteRepositoryAuthenticatorTest {

    private static final String EXPECTED_URL = "http://remoteRepositoryURL:1234";

    /** Test method for
     * {@link RemoteRepositoryAuthenticator#RemoteRepositoryAuthenticator(String, WebClient.Builder)}. */
    @Test
    final void testRemoteRepositoryAuthenticator() {

        // Given
        WebClient.Builder webClientBuilder =
                mockWebClientBuilder(EXPECTED_URL, mock(WebClient.class));

        // When
        new RemoteRepositoryAuthenticator(EXPECTED_URL, webClientBuilder);

        // Then
        InOrder inOrder = Mockito.inOrder(webClientBuilder);
        inOrder.verify(webClientBuilder).baseUrl(EXPECTED_URL);
        inOrder.verify(webClientBuilder).build();
        inOrder.verifyNoMoreInteractions();
    }

    /** Test method for
     * {@link RemoteRepositoryAuthenticator#login(String)}. */
    @Test
    final void testLogin() {

        // Given
        LoginResponse responsePayload = new LoginResponse("eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjE1OTY1ODE3MjMsImp0aSI6Ing2eE1wdExvOXJFN2pIX2YyYUVIV0EiLCJpYXQiOjE1OTY1ODExMjMsIm5iZiI6MTU5NjU4MTAwMywic3ViIjoiQmFyIn0.gXm9y7BF4cSsR7QmeMwa5ze4Xq-vrWnoFHiwwhyJ0ESI1YHoODQ6GARg32dNO-HAGbmmdpkpY1xSV5ZJPDbRono_3trcL6WJwSaR7Ishb10Fq7XktR0PKmGhdy0WRMAGzesvYNSU-xKXkPx73dHEhGCi27rRdVkM__fyGP_NbIZ0uH43LuEQtNDMCYiy9-vXLhs2jWNmHiRtHl2qUMdEywvKGVEevgF9XgmHqWV5iOBn0Fu1cvt6JujM3HrKVG9n_JJV2P2lA-is7ztNb8Su-Dl5ubJiA63BHiQEC5KIbscJvFHueaLMNsPtwr4c53h8K5amDIKKox1Cl59BX03Mcg");
        WebClient webClientMock = mockWebClient("/login", POST, APPLICATION_JSON, "username", "myUsernameXYZ", Stream.of(responsePayload));
        WebClient.Builder webClientBuilder = mockWebClientBuilder(EXPECTED_URL, webClientMock);

        // When
        Optional<String> jwt = new RemoteRepositoryAuthenticator(EXPECTED_URL, webClientBuilder).login("myUsernameXYZ");

        // Then
        assertEquals(responsePayload.getToken(), jwt.get());
    }
}
