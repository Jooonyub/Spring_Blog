package com.hanghae.blog.controller;

import com.hanghae.blog.domain.Article;
import com.hanghae.blog.domain.ArticleRepository;
import com.hanghae.blog.service.ArticleService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ArticleViewController {

    private final ArticleRepository articleRepository;

    @GetMapping("/api/articles/detail/{id}")
    public String readOneArticle(Model model,Article article, @PathVariable Long id) {
        //Optional<Article> article = articleRepository.findById(id);
        model.addAttribute("article", articleRepository.findById(article.getId()).get());
        //System.out.println(article);
        return "articleview.html";
    }


/*
    @Getter
    @Setter
    static class ReadOneArticle {
        private Long id;
        private String title;
        private String username;
    }

 */
}
    /*
    //@GetMapping("/api/articles/?id={id}")
    @GetMapping("/api/articles/detail")
    public String readOneArticle(Model model,@PathVariable Long id) {
        Optional<Article> article = articleRepository.findById(id);
        model.addAttribute("article", article);
        System.out.println(article);
        return "articleview.html";
    }


    @Getter
    @Setter
    static class ReadOneArticle {
        private Long id;
        private String title;
        private String username;
    }

     */


    /*
    @GetMapping("/api/articles/?id={id}")
    @ResponseBody
    public String readOneArticle(@RequestParam("id") Long id, Model model) {
        //model.addAttribute("data", id);
        ReadOneArticle readonearticle = new ReadOneArticle();
        return "articleview";
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    static class ReadOneArticle {
        private Long id;

    }
    */

    /*
    @GetMapping("/api/articles/?id={id}")
    public String readOneArticle(Model model, @PathVariable Long id) {
        model.addAttribute("data", "id");
        return "articleview";
    }

     */

    /*
    @GetMapping("/api/articles/?id={id}")
    public String readOneArticle(@PathVariable Long id) {
        //public List<Article> readOneArticle(@PathVariable Long id) {
        //return articleRepository.findById(id);
        //return articleRepository.findbyId(id);
        return "articleview";
    }

     */

