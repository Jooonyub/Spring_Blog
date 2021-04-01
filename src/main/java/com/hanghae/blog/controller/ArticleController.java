package com.hanghae.blog.controller;

import com.hanghae.blog.model.Article;
import com.hanghae.blog.repository.ArticleRepository;
import com.hanghae.blog.dto.ArticleRequestDto;
import com.hanghae.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    //Post
    //@ResponseBody
    @PostMapping("/api/articles")
    public Article createArticle(@RequestBody ArticleRequestDto requestDto) {
        Article article = new Article(requestDto);
        return articleRepository.save(article);
    }

    //Get
    //@ResponseBody
    @GetMapping("/api/articles")
    public List<Article> readArticle() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/api/articles/{id}")
    public Article getDetail(@PathVariable Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("null"));
    }

    //@ResponseBody
    @DeleteMapping("/api/articles/{id}")
    public Long deleteArticle(@PathVariable Long id) {
        articleRepository.deleteById(id);
        return id;
    }

    //@ResponseBody
    @PutMapping("/api/articles/{id}")
    public Long updateArticle(@PathVariable Long id, @RequestBody ArticleRequestDto requestDto) {
        articleService.updatearticle(id, requestDto);
        return id;
    }

}

