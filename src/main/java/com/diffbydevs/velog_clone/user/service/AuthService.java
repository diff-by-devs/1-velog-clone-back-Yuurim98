package com.diffbydevs.velog_clone.user.service;

import com.diffbydevs.velog_clone.common.exception.CustomException;
import com.diffbydevs.velog_clone.common.exception.ErrorCode;
import com.diffbydevs.velog_clone.user.controller.dto.LoginReqDto;
import com.diffbydevs.velog_clone.user.controller.dto.RegisterReqDto;
import com.diffbydevs.velog_clone.user.domain.AccountModel;
import com.diffbydevs.velog_clone.user.repository.UserRepository;
import com.diffbydevs.velog_clone.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(RegisterReqDto registerReqDto) {
        validateDuplicateAccount(registerReqDto.getUserId());

        validatePasswordMatch(registerReqDto.getPassword(), registerReqDto.getPasswordConfirm());

        User user = registerReqDto.toUser(passwordEncoder.encode(registerReqDto.getPassword()));
        userRepository.save(user);
    }

    /**
     * 로그인 메서드
     *
     * @param loginReqDto
     * @return userId(String)
     * @throws CustomException USER_NOT_FOUND, PASSWORD_MISMATCH
     * @apiNote 1. 이메일로 사용자를 조회 - 없다면 예외 USER_NOT_FOUND 404 <br/>
     * 2. 비밀번호 일치 여부 검증 - 일치하지 않는다면 예외 PASSWORD_MISMATCH 401 <br/>
     * 3. 성공 시 세션에 저장할 userId를 반환
     */
    public String login(LoginReqDto loginReqDto) {

        User user = findUserByEmailOrThrow(loginReqDto);

        AccountModel accountModel = AccountModel.from(user);

        accountModel.verifyPassword(passwordEncoder, loginReqDto.getPassword());

        return accountModel.getUserId();
    }

    private User findUserByEmailOrThrow(LoginReqDto loginReqDto) {
        return userRepository.findByEmail(loginReqDto.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateDuplicateAccount(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.ACCOUNT_CONFLICT);
        }
    }

    private void validatePasswordMatch(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new CustomException(ErrorCode.PASSWORD_CONFIRM_MISMATCH);
        }
    }

}
