package com.diffbydevs.velog_clone.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffbydevs.velog_clone.common.exception.CustomException;
import com.diffbydevs.velog_clone.common.exception.ErrorCode;
import com.diffbydevs.velog_clone.user.controller.dto.RegisterReqDto;
import com.diffbydevs.velog_clone.user.repository.entity.User;
import com.diffbydevs.velog_clone.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Spy
    private PasswordEncoder passwordEncoder;

    @DisplayName("이미 존재하는 id로 회원가입 요청할 경우 예외 발생")
    @Test
    void shouldThrowException_whenUserIdAlreadyExists() {
        // given
        RegisterReqDto reqDto = getRegisterReqDto();
        when(userRepository.existsByUserId(anyString())).thenReturn(true);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.createUser(reqDto));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ACCOUNT_CONFLICT);
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.ACCOUNT_CONFLICT.getMessage());
    }

    @DisplayName("입력한 비밀번호가 일치하지 않은 경우 예외 발생")
    @Test
    void shouldThrowException_whenPasswordsDoNotMatch() {
        // given
        RegisterReqDto reqDto = getRegisterReqDto("Password1!");

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> userService.createUser(reqDto));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.PASSWORD_MISMATCH);
        assertThat(exception.getMessage()).isEqualTo(ErrorCode.PASSWORD_MISMATCH.getMessage());
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

    private RegisterReqDto getRegisterReqDto(String password) {
        return RegisterReqDto.builder()
            .userName("홍길동")
            .email("test@gmail.com")
            .password(password)
            .passwordConfirm("Password2!")
            .userId("test_")
            .build();
    }


}