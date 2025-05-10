package com.diffbydevs.velog_clone.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.diffbydevs.velog_clone.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @DisplayName("회원가입 - 이메일 형식이 아닌 경우 400")
    @Test
    void shouldReturn400_whenEmailIsInvalid() throws Exception {
        // given
        String json = """
        {
          "userName": "홍길동",
          "email": "email",
          "password": "Password1!",
          "passwordConfirm": "Password1!",
          "userId": "test_",
          "profileIntro": "안녕하세요"
        }
        """;

        // when & then
        mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("error"))
            .andExpect(jsonPath("$.code").value(400))
            .andExpect(jsonPath("$.message").value("{email} 이메일 형식으로 입력해주세요."))
            .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("회원가입 - 영어, 숫자, 특수문자를 최소 1개 포함하는 8~20자의 비밀번호가 아닌 경우 400")
    @Test
    void shouldReturn400_whenPasswordDoesNotMeetFormat() throws Exception {
        // given
        String json = """
        {
          "userName": "홍길동",
          "email": "test@email.com",
          "password": "password!",
          "passwordConfirm": "password!",
          "userId": "test_",
          "profileIntro": "안녕하세요"
        }
        """;

        // when & then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("error"))
            .andExpect(jsonPath("$.code").value(400))
            .andExpect(jsonPath("$.message").value("{password} 영어, 숫자, 특수문자를 최소 1개 포함하는 8~20자만 입력 가능합니다."))
            .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("회원가입 - 영어 소문자, 숫자, 특수문자(- 와 _) 3~16자의 id가 아닌 경우 400")
    @Test
    void shouldReturn400_whenUserIdDoesNotMeetFormat() throws Exception {
        // given
        String json = """
        {
          "userName": "홍길동",
          "email": "test@email.com",
          "password": "Password1!",
          "passwordConfirm": "Password1!",
          "userId": "test_!!",
          "profileIntro": "안녕하세요"
        }
        """;

        // when & then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("error"))
            .andExpect(jsonPath("$.code").value(400))
            .andExpect(jsonPath("$.message").value("{userId} 영어 소문자, 숫자, 특수문자(- 와 _) 3~16자만 입력 가능합니다."))
            .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("회원가입 성공")
    @Test
    void shouldReturn200_whenRegisterRequestIsValid() throws Exception {
        // given
        String json = """
        {
          "userName": "홍길동",
          "email": "test@email.com",
          "password": "Password1!",
          "passwordConfirm": "Password1!",
          "userId": "test_",
          "profileIntro": "안녕하세요"
        }
        """;

        // when & then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("success"))
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.message").value("회원가입 되었습니다."))
            .andDo(MockMvcResultHandlers.print());

    }




}