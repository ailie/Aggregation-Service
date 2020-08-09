package com.example.aggregator.repository_local;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.aggregator.domain.Account;

/** Proof-of-concept - it really only tests Spring generated code. */
@DataJpaTest
class AccountDAOTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountDAO sut;

    /** Test method for {@link AccountDAO#findByUserId(String)}. */
    @Test
    final void testFindByUserId() {

        // Given
        Account expAccount =
                new Account("remoteId99", "userId99", "update99", "name99", "product99", "status99", "type99", new BigDecimal(1.123));
        entityManager.persist(expAccount);

        // When
        Iterable<Account> actAccount = sut.findByUserId("userId99");

        // Then
        assertThat(actAccount.iterator().next()).isEqualTo(expAccount);
    }
}
