package kr.quizle.modules.security;

import kr.quizle.modules.exception.SignInFailException;
import kr.quizle.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomUserDetails customUserDetails;

        try {
            customUserDetails = (CustomUserDetails) userService.loadUserByUsername(email);

            if (!passwordEncoder.matches(password, customUserDetails.getPassword())) {
                throw new SignInFailException();
            }

        } catch (UsernameNotFoundException exception) {
            throw new SignInFailException();
        }

        return new UsernamePasswordAuthenticationToken(customUserDetails, password, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
