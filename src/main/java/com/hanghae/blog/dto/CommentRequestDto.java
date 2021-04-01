package com.hanghae.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
    private Long article_id;
    private String username;
    private String comment_contents;
}
