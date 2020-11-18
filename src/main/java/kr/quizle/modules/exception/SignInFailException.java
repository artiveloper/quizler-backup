package kr.quizle.modules.exception;

import kr.quizle.modules.error.BusinessException;

public class SignInFailException extends BusinessException {

    public SignInFailException() {
        super("signInFail");
    }

}
