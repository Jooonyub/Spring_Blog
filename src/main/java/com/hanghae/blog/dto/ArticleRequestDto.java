package com.hanghae.blog.dto;

import com.hanghae.blog.model.User;
import lombok.Getter;

@Getter
public class ArticleRequestDto {
    private String title;
    private String user;
    private String contents;


}
