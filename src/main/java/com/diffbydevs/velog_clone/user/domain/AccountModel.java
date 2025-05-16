package com.diffbydevs.velog_clone.user.domain;

import com.diffbydevs.velog_clone.common.exception.CustomException;
import com.diffbydevs.velog_clone.common.exception.ErrorCode;
import com.diffbydevs.velog_clone.user.repository.entity.User;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class AccountModel {

    private String email;

    private String password;

    private String userId;


    private AccountModel(String email, String password, String userId) {
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    public static AccountModel from(User user) {
        return new AccountModel(user.getEmail(), user.getPassword(), user.getUserId());
    }

    public void verifyPassword(PasswordEncoder passwordEncoder, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, password)) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
