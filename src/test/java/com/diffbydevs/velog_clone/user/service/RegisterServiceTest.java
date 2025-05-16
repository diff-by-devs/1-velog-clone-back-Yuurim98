package com.diffbydevs.velog_clone.user.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffbydevs.velog_clone.common.exception.CustomException;
import com.diffbydevs.velog_clone.common.exception.ErrorCode;
import com.diffbydevs.velog_clone.user.controller.dto.RegisterReqDto;
import com.diffbydevs.velog_clone.user.repository.UserRepository;
import com.diffbydevs.velog_clone.user.repository.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService userService;

    @Spy
    private PasswordEncoder passwordEncoder;

    @DisplayName("이미 존재하는 id로 회원가입 요청할 경우 예외 발생")
    @Test
    void shouldThrowException_whenUserIdAlreadyExists() {
        // given
        RegisterReqDto reqDto = getRegisterReqDto();
        when(userRepository.existsByUserId(anyString())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.createUser(reqDto))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.ACCOUNT_CONFLICT.getMessage())
            .extracting("errorCode")
            .isEqualTo(ErrorCode.ACCOUNT_CONFLICT);
    }

    @DisplayName("입력한 비밀번호가 일치하지 않은 경우 예외 발생")
    @Test
    void shouldThrowException_whenPasswordsDoNotMatch() {
        // given
        RegisterReqDto reqDto = getRegisterReqDto("Password1!", "Password2!");

        // when & then
        assertThatThrownBy(() -> userService.createUser(reqDto))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.PASSWORD_CONFIRM_MISMATCH.getMessage())
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
    }

    @DisplayName("회원가입에 성공하면 save 메서드를 호출")
    @Test
    void shouldCallSaveMethod_whenRegisterSucceeds() {
        // given
        final RegisterReqDto reqDto = getRegisterReqDto();

        // when
        userService.createUser(reqDto);

        // then
        verify(userRepository).save(any(User.class));
    }

    private RegisterReqDto getRegisterReqDto() {
        return RegisterReqDto.builder()
            .userName("홍길동")
            .email("test@gmail.com")
            .password("Password1!")
            .passwordConfirm("Password1!")
            .userId("test_")
            .build();
    }

    private RegisterReqDto getRegisterReqDto(String password, String passwordConfirm) {
        return RegisterReqDto.builder()
            .userName("홍길동")
            .email("test@gmail.com")
            .password(password)
            .passwordConfirm(passwordConfirm)
            .userId("test_")
            .build();
    }


}