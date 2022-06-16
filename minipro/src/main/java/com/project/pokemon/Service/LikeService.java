package com.project.pokemon.Service;

import com.project.pokemon.model.TokenDecode;
import com.project.pokemon.model.dto.requestDto.LikeDto;
import com.project.pokemon.model.entity.Likes;
import com.project.pokemon.model.entity.Pokemon;
import com.project.pokemon.model.entity.Users;
import com.project.pokemon.model.repository.LikeRepository;
import com.project.pokemon.model.repository.PokemonRepository;
import com.project.pokemon.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final PokemonRepository pokemonRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void uplike(TokenDecode decode,
                       Long pokemonId, LikeDto behavior) {

        Users user = userRepository.findById(decode.getId()).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(
                () -> new IllegalArgumentException("해당 포켓몬이 존재하지 않습니다.")
        );

        Likes like = new Likes(user, pokemon);

        if(behavior.getBehavior().equals("like")){
            likeRepository.save(like);
        }else if(behavior.getBehavior().equals("unlike")){
            likeRepository.delete(likeRepository.findByUserAndPokemon(user, pokemon));
        }

        Long count = (long) likeRepository.findAllByPokemon(pokemon).size();
        pokemon.setLikesCnt(count);
    }

    //해당 게시글 유저의 좋아요 유무 전달
    public boolean getLike(Long pokemonId, TokenDecode decode) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(
                ()-> new NullPointerException("해당 포켓몬이 없습니다.")
        );

        Users user = userRepository.findById(decode.getId()).orElseThrow(
                ()-> new NullPointerException("해당 유저가 존재하지 않습니다.")
        );

        return likeRepository.existsByPokemonAndUser(pokemon, user);
    }
}
