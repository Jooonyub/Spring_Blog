package com.hanghae.blog.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter //클래스 정보를 가져올 때
@RequiredArgsConstructor    //기본생성자 자동생성 - 새로운 변수 선언시 필요
@Entity //테이블과 연계됨을 스프링에게 알려줌
public class Article extends Timestamped{
    //제목, 작성자명, 작성내용
    //작성날짜의 경우 LocalDateTime 상속

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    //얘는 좀 더 생각해보기(당장 작성글을 쓰는 것은 아니기 때문에..)
    @Column(nullable = false)
    private String contents;

    public Article(String title, String username, String contents) {
        this.title = title;
        this.username = username;
        this.contents = contents;
    }

    public Article(ArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

    //업데이트 메소드 추가하기
    public void update(ArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }


}
