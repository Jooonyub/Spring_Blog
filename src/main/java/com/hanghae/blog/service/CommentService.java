package com.hanghae.blog.service;

import com.hanghae.blog.dto.CommentRequestDto;
import com.hanghae.blog.model.Comment;
import com.hanghae.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long comment_update(Long id, CommentRequestDto commentReqeustDto){
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 아이디가 없습니다.")
        );
        comment.comment_update(commentReqeustDto);
        return comment.getId();

    }

}
