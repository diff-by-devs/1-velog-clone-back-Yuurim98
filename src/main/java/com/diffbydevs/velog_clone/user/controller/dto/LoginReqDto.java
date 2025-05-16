package com.diffbydevs.velog_clone.user.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 로그인 시 사용되는 DTO
 */
@Getter
@RequiredArgsConstructor
public class LoginReqDto {

    @NotBlank
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.\\w+$",
        message = "이메일 형식으로 입력해주세요.")
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
        message = "영어, 숫자, 특수문자를 최소 1개 포함하는 8~20자만 입력 가능합니다.")
    private final String password;

}
