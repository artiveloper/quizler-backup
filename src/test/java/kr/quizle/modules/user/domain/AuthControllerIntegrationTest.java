package kr.quizle.modules.user.domain;


import com.fasterxml.jackson.databind.ObjectMapper;
import kr.quizle.MockMvcTest;
import kr.quizle.modules.user.dto.SignInRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
public class AuthControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    final String ADMIN_EMAIL = "admin@gmail.com";
    final String ADMIN_PASSWORD = "1234567890";

    @BeforeEach
    void setUp() {
        User admin = User.builder()
                .email(ADMIN_EMAIL)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .build();

        userRepository.save(admin);
    }

    @DisplayName("1. 로그인 실패 - 입력값 오류 (이메일)")
    @Test
    void 로그인_실패_테스트1() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email("admin")
                .password("1234567890")
                .build();

        ResultActions resultActions = signInAction(signInRequest);

        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0].field", is("email")))
                .andExpect(jsonPath("$.errors[0].reason", is("이메일을 다시 확인해주세요")));
    }

    @DisplayName("2. 로그인 실패 - 입력값 오류 (비밀번호)")
    @Test
    void 로그인_실패_테스트2() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email("admin@gmail.com")
                .password("")
                .build();

        ResultActions resultActions = signInAction(signInRequest);

        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code[0].field").value("password"))
                .andExpect(jsonPath("$.errors[0].reason").value("비밀번호를 다시 확인해주세요"));
    }

    @DisplayName("3. 로그인 실패 - 이메일 또는 비밀번호 불일치")
    @Test
    void 로그인_실패_이메일_또는_비밀번호_불일치() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email(ADMIN_EMAIL + "error")
                .password(ADMIN_PASSWORD + "error")
                .build();

        ResultActions resultActions = signInAction(signInRequest);

        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("AE01"))
                .andExpect(jsonPath("$.message").value("이메일과 비밀번호를 확인해주세요"));
    }

    @DisplayName("4. 로그인 성공 - accessToken / tokenType 리턴")
    @Test
    void 로그인_성공() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .email(ADMIN_EMAIL)
                .password(ADMIN_PASSWORD)
                .build();

        ResultActions resultActions = signInAction(signInRequest);

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    private ResultActions signInAction(SignInRequest signInRequest) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        return mockMvc.perform(post("/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print());
    }

}
