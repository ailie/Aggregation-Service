package com.example.aggregator.domain_logic;

import static java.util.Arrays.asList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.example.aggregator.domain.Account;
import com.example.aggregator.domain.Transaction;
import com.example.aggregator.domain.User;
import com.example.aggregator.repository_local.AccountDAO;
import com.example.aggregator.repository_local.TransactionDAO;
import com.example.aggregator.repository_local.UserDAO;
import com.example.aggregator.repository_remote.RemoteRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class DataHandlerTest {

    @Mock
    private UserDAO          userDAO;
    @Mock
    private AccountDAO       accountDAO;
    @Mock
    private TransactionDAO   transactionDAO;
    @Mock
    private RemoteRepository remoteRepository;

    private DataHandler sut;

    @BeforeEach
    void setUp() throws Exception {
        sut = new DataHandler(userDAO, accountDAO, transactionDAO, remoteRepository);
    }

    /** Test method for {@link DataHandler#listUsers()}. */
    @Test
    final void testListUsers() {
        sut.listUsers();
        verify(userDAO).findAll();
        verifyNoMoreInteractions(userDAO);
    }

    /** Test method for {@link DataHandler#listAccountsByUser(String)}. */
    @Test
    final void testListAccountsByUser() {
        String userId = "foo";
        sut.listAccountsByUser(userId);
        verify(accountDAO).findByUserId(userId);
        verifyNoMoreInteractions(userDAO);
    }

    /** Test method for {@link DataHandler#listTransactionByAccount(String)}. */
    @Test
    final void testListTransactionByAccount() {
        String accountId = "foo";
        sut.listTransactionByAccount(accountId);
        verify(transactionDAO).findByAccountId(accountId);
        verifyNoMoreInteractions(userDAO);
    }

    /** Test method for {@link DataHandler#onStart()}. */
    @Test
    final void testOnStart() {

        // Given

        when(userDAO.count()).thenReturn((long) 0);
        when(userDAO.findAll()).thenReturn(asList(new User("userId1", "foo")));

        Account a1 = mock(Account.class);
        Account a2 = mock(Account.class);
        Transaction tA = mock(Transaction.class);
        Transaction tB = mock(Transaction.class);

        when(remoteRepository.findAllAccounts("userId1")).thenReturn(Stream.of(a1, a2));
        when(remoteRepository.findAllTransactions("userId1")).thenReturn(Stream.of(tA, tB));

        // When
        sut.onStart();

        // Then

        verify(userDAO).count();
        verify(userDAO, times(2)).save(any());
        verify(accountDAO).save(a1);
        verify(accountDAO).save(a2);
        verify(transactionDAO).save(tA);
        verify(transactionDAO).save(tA);
        verify(transactionDAO).save(tB);

        verifyNoMoreInteractions(userDAO, accountDAO, transactionDAO, remoteRepository);
    }

    /** Test method for {@link DataHandler#updateLocalStorage()}. */
    @SuppressWarnings("unchecked")
    @Test
    final void testUpdateLocalStorage() {

        // Given

        when(userDAO.findAll()).thenReturn(asList(new User("userId1", "foo")));

        Account a1 = mock(Account.class);
        Account a2 = mock(Account.class);
        Transaction tA = mock(Transaction.class);
        Transaction tB = mock(Transaction.class);

        when(remoteRepository.findAllAccounts("userId1")).thenReturn(Stream.of(a1, a2));
        when(remoteRepository.findAllTransactions("userId1")).thenReturn(Stream.of(tA, tB));

        // When
        sut.updateLocalStorage();

        // Then

        verify(accountDAO).save(a1);
        verify(accountDAO).save(a2);
        verify(transactionDAO).save(tA);
        verify(transactionDAO).save(tA);
        verify(transactionDAO).save(tB);

        verifyNoMoreInteractions(userDAO, accountDAO, transactionDAO, remoteRepository);
    }
}
