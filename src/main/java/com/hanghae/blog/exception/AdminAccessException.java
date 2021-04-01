package com.hanghae.blog.exception;

public class AdminAccessException extends Exception{
    @Override
    public String getMessage() {
        return "관리자 암호가 틀려 등록이 불가능합니다.";
    }
}
