package com.project.pokemon.Controller;

import com.project.pokemon.model.TokenDecode;
import com.project.pokemon.model.dto.requestDto.CommentsDto;
import com.project.pokemon.model.dto.responseDto.CommentsListDto;
import com.project.pokemon.Service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class CommentsController {
    private final CommentsService commentsService;

    //댓글 조회 API
    @GetMapping("/viewcomments/{pokemonId}")
    public List<CommentsListDto> getComments(@PathVariable Long pokemonId) {
        return commentsService.getComments(pokemonId);
    }

    //댓글 작성 API
    @PostMapping("/comment/{pokemonId}")
    public String createComments(@PathVariable Long pokemonId,
                                 @RequestBody CommentsDto commentsDto,
                                 HttpServletRequest httpRequest) {
        TokenDecode decode = (TokenDecode) httpRequest.getAttribute("decode");
        return commentsService.createComments(pokemonId, commentsDto, decode);
    }

    //댓글 수정 API
    @PatchMapping("/comment/{commentId}")
    public String updateComments(@PathVariable Long commentId,
                                 @RequestBody CommentsDto commentsDto,
                                 HttpServletRequest httpRequest) {
        TokenDecode decode = (TokenDecode) httpRequest.getAttribute("decode");
        return commentsService.updateComments(commentId, commentsDto, decode);
    }

    //댓글 삭제 API
    @DeleteMapping("/comment/{commentId}")
    public String deleteComments(@PathVariable Long commentId,
                                 HttpServletRequest httpRequest) {
        TokenDecode decode = (TokenDecode) httpRequest.getAttribute("decode");
        return commentsService.deleteComments(commentId, decode);
    }
}
