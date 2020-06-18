package com.cantet.refacto.user.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UserUTest {

    @Nested
    class ConstructorShould {

        @Nested
        class Bind {

            public static final String USER_ID = "1111";
            public static final String NAME = "toto";
            public static final String EMAIL = "toto@test.com";
            public final Date CREATED = new Date();
            public final Date LAST_CONNECTION = new Date();
            private User user;

            @BeforeEach
            void setUp() throws InvalidFieldException {
                user = new User(USER_ID, NAME, EMAIL, CREATED, LAST_CONNECTION);
            }

            @Test
            void id() {
                // then
                assertThat(user.getUserId()).isEqualTo(USER_ID);
            }

            @Test
            void name() {
                // then
                assertThat(user.getName()).isEqualTo(NAME);
            }

            @Test
            void email() {
                // then
                assertThat(user.getEmail()).isEqualTo(EMAIL);
            }

            @Test
            void created() {
                // then
                assertThat(user.getCreated()).isEqualTo(CREATED);
            }

            @Test
            void lastConnexion() {
                // then
                assertThat(user.getLastConnection()).isEqualTo(LAST_CONNECTION);
            }
        }

        @Test
        void throw_InvalidFieldException_when_name_is_empty() {
            // when
            final Throwable throwable = catchThrowable(() -> new User("1111", "", "toto@test.com", new Date(), new Date()));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }

        @Test
        void throw_InvalidFieldException_when_email_is_empty() {
            // when
            final Throwable throwable = catchThrowable(() -> new User("1111", "toto", "", new Date(), new Date()));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }
    }
}