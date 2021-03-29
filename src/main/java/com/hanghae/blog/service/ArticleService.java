package com.hanghae.blog.service;

import com.hanghae.blog.domain.Article;
import com.hanghae.blog.domain.ArticleRepository;
import com.hanghae.blog.domain.ArticleRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {
    //업데이트 메소드 생성
    private final ArticleRepository articleRepository;


    @Transactional
    public Long update(Long id, ArticleRequestDto requestDto) {
        Article article = articleRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        article.update(requestDto);
        return id;
    }


/*
    public Article readOneArticle(Long id, Article article) {
        //Article article = articleRepository.findById(id);
        return articleRepository.findById(id);
        // return findById(article.getId()).get();
    }

 */

    /*
    public Article readOneArticle(Article article) {
        return findById(article.getId()).get();
    }

     */
}
