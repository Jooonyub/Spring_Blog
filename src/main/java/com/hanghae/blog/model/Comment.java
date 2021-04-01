package com.hanghae.blog.model;

import com.hanghae.blog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped{
    //댓글에 포함되어야 할 내용들

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    /*
    //article의 title은 겹칠 수 있으므로 고유한 값으로 접근하자
    @Column(nullable = false)
    private String articletitle;
     */
    @Column(nullable = false)
    private Long article_id;

    @Column(nullable=false)
    private String username;


    @Column
    private String comment_contents;

    public Comment (CommentRequestDto commentRequestDto) {
        this.article_id = commentRequestDto.getArticle_id();
        this.username = commentRequestDto.getUsername();
        this.comment_contents = commentRequestDto.getComment_contents();
    }

    public void comment_update(CommentRequestDto commentRequestDto) {
        this.article_id = commentRequestDto.getArticle_id();
        this.username = commentRequestDto.getUsername();
        this.comment_contents = commentRequestDto.getComment_contents();
    }
}
