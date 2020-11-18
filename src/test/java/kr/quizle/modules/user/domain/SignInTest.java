package kr.quizle.modules.user.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.quizle.modules.user.dto.SignInRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
public class SignInTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        User admin = User.builder()
                .email("admin@gmail.com")
                .password("1234567890")
                .build();


    }

    @DisplayName("1. 로그인을 시도한다.")
    @Test
    void login() throws Exception {
        String email = "admin@gmail.com";
        String password = "1234567890";

        SignInRequest signInRequest = SignInRequest.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        mockMvc.
                perform(
                        post("/v1/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print());
    }

}
