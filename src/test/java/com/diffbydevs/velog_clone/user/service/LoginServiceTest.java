package com.diffbydevs.velog_clone.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.diffbydevs.velog_clone.common.exception.CustomException;
import com.diffbydevs.velog_clone.common.exception.ErrorCode;
import com.diffbydevs.velog_clone.user.controller.dto.LoginReqDto;
import com.diffbydevs.velog_clone.user.repository.UserRepository;
import com.diffbydevs.velog_clone.user.repository.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService userService;

    @Spy
    private PasswordEncoder passwordEncoder;

    @DisplayName("사용자가 존재하지 않는 경우 예외 발생")
    @Test
    void login_shouldThrowException_whenEmailNotFound() {
        // given
        LoginReqDto reqDto = getLoginReqDto("test@gmail.com", "Password1!");
        when(userRepository.findByEmail(reqDto.getEmail())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.login(reqDto))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage())
            .extracting("errorCode")
            .isEqualTo(ErrorCode.USER_NOT_FOUND);
    }

    @DisplayName("비밀번호가 일치하지 않는 경우 예외 발생")
    @Test
    void login_shouldThrowException_whenPasswordDoesNotMatch() {
        // given
        LoginReqDto reqDto = getLoginReqDto("test@gmail.com", "Password1!");

        when(userRepository.findByEmail(reqDto.getEmail())).thenReturn(
            Optional.ofNullable(getUser()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> userService.login(reqDto))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.PASSWORD_MISMATCH.getMessage())
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PASSWORD_MISMATCH);
    }

    @DisplayName("사용자가 존재하고 비밀번호가 일치하면 userId 반환")
    @Test
    void login_shouldReturnUserId_whenUserExistsAndPasswordMatches() {
        // given
        LoginReqDto reqDto = getLoginReqDto("test@gmail.com", "Password2!");
        User user = getUser();

        when(userRepository.findByEmail(reqDto.getEmail())).thenReturn(
            Optional.ofNullable(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // when
        String result = userService.login(reqDto);
        assertThat(result).isEqualTo("id123");
    }

    private static LoginReqDto getLoginReqDto(String email, String password) {
        return new LoginReqDto(email, password);
    }

    private static User getUser() {
        return User.create("이름", "이메일", "Password2!", "id123", "블로그", "소개");
    }


}
