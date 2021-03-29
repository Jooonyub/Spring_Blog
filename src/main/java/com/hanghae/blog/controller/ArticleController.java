package com.hanghae.blog.controller;

import com.hanghae.blog.domain.Article;
import com.hanghae.blog.domain.ArticleRepository;
import com.hanghae.blog.domain.ArticleRequestDto;
import com.hanghae.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    //Post
    @PostMapping("/api/articles")
    public Article createArticle(@RequestBody ArticleRequestDto requestDto) {
        Article article = new Article(requestDto);
        return articleRepository.save(article);
    }

    //Get
    @GetMapping("/api/articles")
    public List<Article> readArticle() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    /*
    //id, List<String>
    @GetMapping("/api/articles/?id={id}")
    public String readOneArticle(@PathVariable Long id) {
    //public List<Article> readOneArticle(@PathVariable Long id) {
        //return articleRepository.findById(id);
        //return articleRepository.findbyId(id);
        return "articleview.html";
    }

     */
    /*public Optional<Article> readOneArticle(@PathVariable Long id) {
        return articleRepository.findById(id);
    }

     */

    /*@GetMapping("api/articles/{id}")
    public Long readArticle(@PathVariable Long id) {
        return id;
    }

     */


    @DeleteMapping("/api/articles/detail/{id}")
    public Long deleteArticle(@PathVariable Long id) {
        articleRepository.deleteById(id);
        return id;
    }

    @PutMapping("/api/articles/detail/{id}")
    public Long updateArticle(@PathVariable Long id, @RequestBody ArticleRequestDto requestDto) {
        articleService.update(id, requestDto);
        return id;
    }


}

