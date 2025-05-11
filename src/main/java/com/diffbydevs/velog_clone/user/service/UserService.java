package com.diffbydevs.velog_clone.user.service;

import com.diffbydevs.velog_clone.common.exception.CustomException;
import com.diffbydevs.velog_clone.common.exception.ErrorCode;
import com.diffbydevs.velog_clone.user.controller.dto.RegisterReqDto;
import com.diffbydevs.velog_clone.user.repository.entity.User;
import com.diffbydevs.velog_clone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(RegisterReqDto registerReqDto) {
        validateDuplicateAccount(registerReqDto.getUserId());

        validatePasswordMatch(registerReqDto.getPassword(), registerReqDto.getPasswordConfirm());

        User user = registerReqDto.toUser(passwordEncoder.encode(registerReqDto.getPassword()));
        userRepository.save(user);
    }

    private void validateDuplicateAccount(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.ACCOUNT_CONFLICT);
        }
    }

    private static void validatePasswordMatch(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

}
