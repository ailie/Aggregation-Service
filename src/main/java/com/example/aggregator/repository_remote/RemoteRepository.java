package com.example.aggregator.repository_remote;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.aggregator.domain.Account;
import com.example.aggregator.domain.Transaction;

@Service
@PropertySource(value = { "classpath:application.properties" })
public class RemoteRepository {

    private static final Logger LOG = getLogger(RemoteRepository.class);

    /** package-private to allow for test asserts */
    final RemoteRepositoryAuthenticator authenticator;

    private final WebClient                     webClient;

    public RemoteRepository(@Value("${remoteRepositoryURL}") String remoteRepositoryURL,
                            WebClient.Builder webClientBuilder,
                            RemoteRepositoryAuthenticator authenticator) {
        this.webClient = webClientBuilder.baseUrl(remoteRepositoryURL).build();
        this.authenticator = authenticator;
    }

    public Stream<Account> findAllAccounts(String username) {

        Optional<String> jwt = authenticator.login(username);

        if (!jwt.isPresent()) {
            return Stream.empty();
        }

        try {
            return webClient
                    .get()
                    .uri("/accounts")
                    .header("X-AUTH", jwt.get())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(Account.class)
                    .toStream()
                    .peek(e -> e.setUserId(username));
        } catch (Throwable e) {
            LOG.error("Could not load accounts from remote repository !", e);
            return Stream.empty();
        } finally {
            // The resources used by WebClient are managed by the framework
        }
    }

    public Stream<Transaction> findAllTransactions(String username) {

        Optional<String> jwt = authenticator.login(username);

        if (!jwt.isPresent()) {
            return Stream.empty();
        }

        try {
            return webClient
                    .get()
                    .uri("/transactions")
                    .header("X-AUTH", jwt.get())
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(Transaction.class)
                    .toStream();
        } catch (Throwable e) {
            LOG.error("Could not load transactions from remote repository !", e);
            return Stream.empty();
        } finally {
            // The resources used by WebClient are managed by the framework
        }
    }
}
