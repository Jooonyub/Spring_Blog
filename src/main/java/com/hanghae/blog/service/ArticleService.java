package com.hanghae.blog.service;

import com.hanghae.blog.model.Article;
import com.hanghae.blog.repository.ArticleRepository;
import com.hanghae.blog.dto.ArticleRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleService {
    //업데이트 메소드 생성
    private final ArticleRepository articleRepository;


    @Transactional
    public Long updatearticle(Long id, ArticleRequestDto requestDto) {
        Article article = articleRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        article.update(requestDto);
        return article.getId();
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
