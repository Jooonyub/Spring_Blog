package com.hanghae.blog.exception;

public class PasswordIncoincidenceException extends Exception {
    @Override
    public String getMessage() {
        return "비밀번호 확인 입력값이 비밀번호와 일치하지 않습니다.";
    }
}
