package com.cantet.refacto.user.domain.model;

import com.cantet.refacto.user.domain.service.InvalidFieldException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UserUTest {

    @Nested
    class CanBeSavedShould {
        @Test
        void do_nothing_when_name_and_email_are_set() throws InvalidFieldException {
            // given
            final User user = new User("1111", "toto", "toto@test.com", new Date(), new Date());

            // when
            final Boolean result = user.canBeSaved();

            // then
            assertThat(result).isTrue();
        }

        @Test
        void throw_InvalidFieldException_when_name_is_empty() {
            // given
            final User user = new User("1111", "", "toto@test.com", new Date(), new Date());

            // when
            final Throwable throwable = catchThrowable(() -> user.canBeSaved());

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }

        @Test
        void throw_InvalidFieldException_when_email_is_empty() {
            // given
            final User user = new User("1111", "toto", "", new Date(), new Date());

            // when
            final Throwable throwable = catchThrowable(() -> user.canBeSaved());

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }
    }
}