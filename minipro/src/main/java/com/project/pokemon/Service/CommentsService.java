package com.project.pokemon.Service;

import com.project.pokemon.model.TokenDecode;
import com.project.pokemon.model.dto.requestDto.CommentsDto;
import com.project.pokemon.model.dto.responseDto.CommentsListDto;
import com.project.pokemon.model.entity.Comments;
import com.project.pokemon.model.entity.Pokemon;
import com.project.pokemon.model.entity.Users;
import com.project.pokemon.model.repository.CommentsRepository;
import com.project.pokemon.model.repository.PokemonRepository;
import com.project.pokemon.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentsService {


    private final CommentsRepository commentsRepository;
    private final PokemonRepository pokemonRepository;
    private final UserRepository userRepository;


    //댓글 작성 로직
    public String createComments(Long pokemonId,
                                 CommentsDto commentsDto,
                                 TokenDecode decode) {

        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(
                () -> new IllegalArgumentException("해당 포켓몬이 없습니다.")
        );

        Users user = userRepository.findById(decode.getId()).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다.")
        );  //UserDetailsImpl작업 이후 수정 가능

        Comments comments = new Comments(commentsDto, user, pokemon);
        commentsRepository.save(comments);
        return "댓글이 작성되었습니다.";
    }

    //댓글 조회 로직
    public List<CommentsListDto> getComments(Long pokemonId) {
        List<Comments> commentsList = commentsRepository.findAllByPokemonIdOrderByCreatedAtDesc(pokemonId); // (pokemonId);

        return createResponse(commentsList);
    }

    //댓글 수정 로직
    @Transactional
    public String updateComments(Long commentId,
                                 CommentsDto commentsDto,
                                 TokenDecode decode) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );

        if(comments.getUsers().getId().equals(decode.getId())){
            comments.setComments(commentsDto.getComments());
            commentsRepository.save(comments);
            return "댓글이 수정되었습니다.";
        }else{
            return "다른사람의 댓글입니다.";
        }
    }

    //댓글 삭제 로직
    @Transactional
    public String deleteComments(Long commentId, TokenDecode decode) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );
        if(comments.getUsers().getId().equals(decode.getId())){
            commentsRepository.deleteById(commentId);
            return "댓글이 삭제되었습니다.";
        }else{
            return "다른사람의 댓글입니다.";
        }

    }

    // CommentsService에서 필요한 메서드 정의
    //댓글 ResponseDto 생성함수
    private List<CommentsListDto> createResponse(List<Comments> commentsList) {
        List<CommentsListDto> commentsListDtos = new ArrayList<>();

        for (Comments comments : commentsList) {
            CommentsListDto commentsListDto = new CommentsListDto();
            commentsListDto.setComments(comments.getComments());
            commentsListDto.setNickname(comments.getUsers().getNickname());
            commentsListDto.setCreatedAt(comments.getCreatedAt());
            commentsListDtos.add(commentsListDto);
        }

        return commentsListDtos;
    }

}
