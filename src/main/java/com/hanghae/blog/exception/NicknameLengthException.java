package com.hanghae.blog.exception;

public class NicknameLengthException extends Exception {
    @Override
    public String getMessage() {
        return "닉네임은 최소 3자 이상이어야 합니다.";
    }
}
