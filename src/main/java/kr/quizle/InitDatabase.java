package kr.quizle;

import kr.quizle.modules.user.domain.User;
import kr.quizle.modules.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class InitDatabase implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User admin = User.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("123456789"))
                .build();

        userRepository.save(admin);
    }

}
