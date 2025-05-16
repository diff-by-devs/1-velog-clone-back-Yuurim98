package com.diffbydevs.velog_clone.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.diffbydevs.velog_clone.user.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(AuthController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private AuthService userService;

    @DisplayName("로그인 - 이메일 형식이 아닌 경우 400")
    @Test
    void login_shouldReturn400_whenEmailIsInvalid() throws Exception {
        // given
        String json = """
            {
              "email": "email",
              "password": "Password1!"
            }
            """;

        // when & then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("error"))
            .andExpect(jsonPath("$.code").value(400))
            .andExpect(jsonPath("$.message").value("{email} 이메일 형식으로 입력해주세요."))
            .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("로그인 - 영어, 숫자, 특수문자를 최소 1개 포함하는 8~20자의 비밀번호가 아닌 경우 400")
    @Test
    void login_shouldReturn400_whenPasswordDoesNotMeetFormat() throws Exception {
        // given
        String json = """
            {
              "email": "test@email.com",
              "password": "password!"
            }
            """;

        // when & then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("error"))
            .andExpect(jsonPath("$.code").value(400))
            .andExpect(
                jsonPath("$.message").value("{password} 영어, 숫자, 특수문자를 최소 1개 포함하는 8~20자만 입력 가능합니다."))
            .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("로그인 성공 - 응답에 세션을 포함한다")
    @Test
    void login_shouldReturn200_andSetSessionCookie() throws Exception {
        // given
        String json = """
            {
              "email": "test@email.com",
              "password": "Password1!"
            }
            """;

        // when & then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(result -> {
                result.getRequest().getSession(false); // 생성된 세션이 없으면 null 반환
                assertThat(result).isNotNull();
            })
            .andExpect(jsonPath("$.status").value("success"))
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("로그인 되었습니다."))
            .andDo(MockMvcResultHandlers.print());
    }

}
