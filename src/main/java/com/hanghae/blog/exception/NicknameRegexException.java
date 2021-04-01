package com.hanghae.blog.exception;

public class NicknameRegexException extends Exception {
    @Override
    public String getMessage() {
        return "닉네임은 알파벳 대소문자, 숫자로 구성되어야 합니다.";
    }
}
