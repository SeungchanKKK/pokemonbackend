package com.project.pokemon.Controller;

import com.project.pokemon.Service.LikeService;
import com.project.pokemon.model.TokenDecode;
import com.project.pokemon.model.dto.requestDto.LikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("like/{pokemonId}")
    public void uplike(HttpServletRequest httpRequest,
                       @PathVariable Long pokemonId,
                       @RequestBody LikeDto behavior){
        TokenDecode decode = (TokenDecode) httpRequest.getAttribute("decode");
        likeService.uplike(decode, pokemonId, behavior);
    }

    @GetMapping("viewlikes/{pokemonId}")
    public boolean getlike(@PathVariable Long pokemonId,
                           HttpServletRequest httpRequest){
        TokenDecode decode = (TokenDecode) httpRequest.getAttribute("decode");

        return likeService.getLike(pokemonId, decode);
    }
}
