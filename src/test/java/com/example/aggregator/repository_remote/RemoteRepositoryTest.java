package com.example.aggregator.repository_remote;

import static org.assertj.core.util.Arrays.array;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

import com.example.aggregator.domain.Account;
import com.example.aggregator.domain.Transaction;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class RemoteRepositoryTest {

    private static final String EXPECTED_URL = "http://remoteRepositoryURL:1234";

    /**
     * Test method for {@link RemoteRepository#RemoteRepository(java.lang.String, org.springframework.web.reactive.function.client.WebClient.Builder, com.example.aggregator.repository_remote.RemoteRepositoryAuthenticator)}.
     */
    @Test
    final void testRemoteRepository() {

        // Given
        WebClient.Builder webClientBuilder =
                mockWebClientBuilder(EXPECTED_URL, mock(WebClient.class));

        RemoteRepositoryAuthenticator authenticator = mock(RemoteRepositoryAuthenticator.class);

        // When
        RemoteRepository sut = new RemoteRepository(EXPECTED_URL, webClientBuilder, authenticator);

        // Then
        InOrder inOrder = Mockito.inOrder(webClientBuilder);
        inOrder.verify(webClientBuilder).baseUrl(EXPECTED_URL);
        inOrder.verify(webClientBuilder).build();
        inOrder.verifyNoMoreInteractions();

        assertEquals(authenticator, sut.authenticator);
    }

    /**
     * Test method for {@link RemoteRepository#findAllAccounts(com.example.aggregator.domain.User)}.
     */
    @Test
    final void testFindAllAccounts() {

        // Given
        String username = "myUsernameXYZ";
        String jwt = "someLongJWT";

        Account expA1 = mock(Account.class);
        Account expA2 = mock(Account.class);

        WebClient webClientMock = mockWebClient("/accounts", GET, APPLICATION_JSON, "X-AUTH", jwt, Stream.of(expA1, expA2));
        WebClient.Builder webClientBuilder = mockWebClientBuilder(EXPECTED_URL, webClientMock);

        RemoteRepositoryAuthenticator authenticator = mock(RemoteRepositoryAuthenticator.class);
        when(authenticator.login(username)).thenReturn(Optional.of(jwt));

        // When
        Stream<Account> retrievedFromRemote = new RemoteRepository(EXPECTED_URL, webClientBuilder, authenticator).findAllAccounts(username);

        // Then
        assertThat(array(expA1, expA2), is(equalTo(retrievedFromRemote.toArray())));
        verify(expA1).setUserId(username);
        verify(expA2).setUserId(username);
    }

    /**
     * Test method for {@link RemoteRepository#findAllTransactions(com.example.aggregator.domain.User)}.
     */
    @Test
    final void testFindAllTransactions() {

        // Given
        String username = "myUsernameXYZ";
        String jwt = "someLongJWT";

        Transaction expT1 = mock(Transaction.class);
        Transaction expT2 = mock(Transaction.class);

        WebClient webClientMock = mockWebClient("/transactions", GET, APPLICATION_JSON, "X-AUTH", jwt, Stream.of(expT1, expT2));
        WebClient.Builder webClientBuilder = mockWebClientBuilder(EXPECTED_URL, webClientMock);

        RemoteRepositoryAuthenticator authenticator = mock(RemoteRepositoryAuthenticator.class);
        when(authenticator.login(username)).thenReturn(Optional.of(jwt));

        // When
        Stream<Transaction> retrievedFromRemote = new RemoteRepository(EXPECTED_URL, webClientBuilder, authenticator).findAllTransactions(username);

        // Then
        assertThat(array(expT1, expT2), is(equalTo(retrievedFromRemote.toArray())));
    }
}
