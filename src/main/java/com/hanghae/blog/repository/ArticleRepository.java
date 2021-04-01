package com.hanghae.blog.repository;

import com.hanghae.blog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByCreatedAtDesc();

    Optional<Article> findById(Long id);

    /*아래의 둘중 하나로 특정 게시글 조회일것같은데..*/
    //Optional<Article> findById(Long id);

    //List<Article> findById(Long id);

    //ArticleRequestDto findById(Long id);
    //findAllByOrderByModifiedAtDesc();
}
