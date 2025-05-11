package com.diffbydevs.velog_clone.user.controller.dto;

import com.diffbydevs.velog_clone.user.repository.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class RegisterReqDto {

    @NotBlank
    private final String userName;

    @NotBlank
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.\\w+$",
        message = "이메일 형식으로 입력해주세요.")
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
        message = "영어, 숫자, 특수문자를 최소 1개 포함하는 8~20자만 입력 가능합니다.")
    private final String password;

    @NotBlank
    private final String passwordConfirm;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9_-]{3,16}$",
        message = "영어 소문자, 숫자, 특수문자(- 와 _) 3~16자만 입력 가능합니다.")
    private final String userId;

    @Size(max = 300)
    private final String profileIntro;


    public User toUser(String encodingPassword) {
       return User.builder()
           .name(userName)
           .email(email)
           .password(encodingPassword)
           .userId(userId)
           .blogName(userId)
           .profileIntro(profileIntro)
           .build();
    }

}
