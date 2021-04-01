package com.hanghae.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDto {
    private String username;
    private String password;
    private String againpassword;
    //private String email;
    private boolean admin = false;
    //관리자 모드로 가입을 하려한다면 아래의 공백이 입력받은 토큰값이 될 것
    private String adminToken = "";
}
