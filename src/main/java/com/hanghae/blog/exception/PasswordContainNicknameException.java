package com.hanghae.blog.exception;

public class PasswordContainNicknameException extends Exception {
    @Override
    public String getMessage() {
        return "비밀번호는 닉네임과 같은 값이 포함될 수 없습니다.";
    }
}
