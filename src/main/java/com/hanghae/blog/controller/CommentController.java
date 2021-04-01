package com.hanghae.blog.controller;

import com.hanghae.blog.dto.CommentRequestDto;
import com.hanghae.blog.model.Comment;
import com.hanghae.blog.repository.CommentRepository;
import com.hanghae.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    //comments 관련 API controller
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    //전체 댓글 나열
    @GetMapping("/api/comments")
    public List<Comment> getComment(){
        return commentRepository.findAllByOrderByModifiedAtDesc();
    }

    /*
    @GetMapping("/api/comment/{article_id}")
    public List<Comment> getComment(@PathVariable Long article_id){
        return commentRepository.findAll(CommentSpecs.withArticle_id(article_id));
    }
     */

    //댓글 작성
    @PostMapping("/api/comments")
    public Comment createComment(@RequestBody CommentRequestDto commentReqeustDto){
        Comment comment = new Comment(commentReqeustDto);
        return commentRepository.save(comment);
    }

    //댓글 수정
    @PutMapping("/api/comments/{id}")
    public Long updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentReqeustDto){
        return commentService.comment_update(id, commentReqeustDto);
    }

    //댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public Long deleteComment(@PathVariable Long id){
        commentRepository.deleteById(id);
        return id;
    }
}
