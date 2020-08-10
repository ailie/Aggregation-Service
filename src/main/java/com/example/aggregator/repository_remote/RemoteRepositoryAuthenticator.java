package com.example.aggregator.repository_remote;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@PropertySource(value = { "classpath:application.properties" })
class RemoteRepositoryAuthenticator {

    private static final Logger LOG = getLogger(RemoteRepositoryAuthenticator.class);

    private final WebClient webClient;

    public RemoteRepositoryAuthenticator(@Value("${remoteRepositoryURL}") String remoteRepositoryURL,
                                         WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(remoteRepositoryURL).build();
    }

    /** @param username
     *            the user to log into the remote repository
     * @return the JSON Web Token needed for further calls to the remote repository, or
     *         nothing if the login was unsuccessful */
    Optional<String> login(String username) {
        try {
            return webClient.post()
                    .uri("/login")
                    .header("username", username)
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(LoginResponse.class)
                    .toStream()
                    .map(LoginResponse::getToken)
                    .findFirst();
        } catch (Throwable e) {
            LOG.error("Could not login against the remote repository !", e);
            return Optional.empty();
        }
    }

    static class LoginResponse {

        private String token;

        LoginResponse() {
        }

        LoginResponse(String token) {
            this.token = token;
        }

        String setToken(String token) {
            return this.token = token;
        }

        String getToken() {
            return token;
        }
    }
}
