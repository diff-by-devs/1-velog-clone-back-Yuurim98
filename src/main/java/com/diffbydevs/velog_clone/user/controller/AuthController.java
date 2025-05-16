package com.diffbydevs.velog_clone.user.controller;

import com.diffbydevs.velog_clone.common.response.ApiResponse;
import com.diffbydevs.velog_clone.user.controller.dto.LoginReqDto;
import com.diffbydevs.velog_clone.user.controller.dto.RegisterReqDto;
import com.diffbydevs.velog_clone.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원가입, 로그인 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
        @RequestBody @Valid RegisterReqDto registerReqDto) {
        userService.createUser(registerReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("회원가입 되었습니다."));
    }

    /**
     * 로그인 API
     *
     * @param loginReqDto 이메일과 비밀번호를 필드로 가지는 DTO
     * @param request     세션 저장 시 사용
     * @return 세션ID를 포함하여 성공 응답 반환
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid LoginReqDto loginReqDto,
        HttpServletRequest request) {
        String userId = userService.login(loginReqDto);

        HttpSession session = request.getSession();

        session.setAttribute("user", userId);
        return ResponseEntity.ok(ApiResponse.success("로그인 되었습니다."));
    }
}
