package com.diffbydevs.velog_clone.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.diffbydevs.velog_clone.common.exception.CustomException;
import com.diffbydevs.velog_clone.common.exception.ErrorCode;
import com.diffbydevs.velog_clone.user.repository.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthModelTest {

    @Mock
    private PasswordEncoder passwordEncoder;


    @DisplayName("User 엔티티로부터 AuthModel 생성")
    @Test
    void shouldCreateAuthModelFromUser() {
        // given & when
        User user = getUser();
        AuthModel authModel = getAuthModel(user);

        // then
        assertThat(authModel.getEmail()).isEqualTo(user.getEmail());
        assertThat(authModel.getPassword()).isEqualTo(user.getPassword());
        assertThat(authModel.getUserId()).isEqualTo(user.getUserId());
    }

    @DisplayName("비밀번호가 일치하면 예외 없이 통과")
    @Test
    void shouldPass_whenPasswordMatches() {
        // given
        String rawPassword = "Password1!";
        AuthModel authModel = getAuthModel(getUser());

        when(passwordEncoder.matches(rawPassword, authModel.getPassword())).thenReturn(true);

        // when & then
        assertThatCode(() -> authModel.verifyPassword(passwordEncoder, rawPassword))
            .doesNotThrowAnyException();
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외 발생")
    @Test
    void shouldThrowException_whenPasswordDoesNotMatch() {
        // given
        String rawPassword = "wrongPassword!";
        AuthModel authModel = getAuthModel(getUser());

        when(passwordEncoder.matches(rawPassword, authModel.getPassword())).thenReturn(false);


        // when & then
        assertThatThrownBy(() -> authModel.verifyPassword(passwordEncoder, rawPassword))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.PASSWORD_MISMATCH.getMessage())
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PASSWORD_MISMATCH);
    }

    private static AuthModel getAuthModel(User user) {
        return AuthModel.from(user);
    }

    private static User getUser() {
        return User.create("이름", "이메일", "Password1!", "id123", "블로그", "소개");
    }
}