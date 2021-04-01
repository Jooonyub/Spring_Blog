package com.hanghae.blog.exception;

public class NicknameDuplicationException extends Exception{
    @Override
    public String getMessage() {
        return "중복된 닉네임입니다.";
    }
}
