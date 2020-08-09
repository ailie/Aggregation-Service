package com.example.aggregator.repository_local;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.aggregator.domain.User;

/** Proof-of-concept - it really only tests Spring generated code. */
@DataJpaTest
class UserDAOTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDAO sut;

    /** Test method for {@link UserDAO#count()}. */
    @Test
    final void testCount() {

        // Given
        entityManager.persist(new User("fbar", "Foo Bar"));

        // When
        long count = sut.count();

        // Then
        assertThat(1).isEqualTo(count);
    }
}
