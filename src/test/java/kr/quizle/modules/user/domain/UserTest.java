package kr.quizle.modules.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoderTest() {
        String password = "12345678";
        String encodePassword = passwordEncoder.encode(password);
        System.out.println("encodePassword = " + encodePassword);
        boolean matches = passwordEncoder.matches(password, encodePassword);
        assertThat(matches).isTrue();
    }

}