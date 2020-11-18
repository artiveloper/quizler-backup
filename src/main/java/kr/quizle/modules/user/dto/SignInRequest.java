package kr.quizle.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class SignInRequest {

    @Email(message = "{email.notBlank}")
    @NotBlank(message = "{email.notBlank}")
    private String email;

    @NotBlank(message = "{password.notBlank}")
    private String password;

    @Builder
    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
