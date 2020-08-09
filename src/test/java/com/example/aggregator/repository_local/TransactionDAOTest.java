package com.example.aggregator.repository_local;

import static java.math.BigDecimal.valueOf;

import static org.assertj.core.api.Assertions.assertThat;

import static com.example.aggregator.domain.Currency.GBP;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.aggregator.domain.Transaction;

/** Proof-of-concept - it really only tests Spring generated code. */
@DataJpaTest
class TransactionDAOTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionDAO sut;

    /** Test method for {@link TransactionDAO#findByAccountId(String)}. */
    @Test
    final void testFindByAccountId() {

        // Given
        String accountId = "xDaig0die";
        Transaction expTransaction =
                new Transaction("id123", accountId, null, null, null, null, "status", GBP, valueOf(1.123), "update", "description");
        entityManager.persist(expTransaction);

        // When
        Iterable<Transaction> actTransaction = sut.findByAccountId(accountId);

        // Then
        assertThat(actTransaction.iterator().next().getAccountId()).isEqualTo(accountId);
    }
}
