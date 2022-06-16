package com.project.pokemon.model.repository;

import com.project.pokemon.model.entity.Likes;
import com.project.pokemon.model.entity.Pokemon;
import com.project.pokemon.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Likes findByUserAndPokemon(Users user, Pokemon pokemon);
    List<Likes> findAllByPokemon(Pokemon pokemon);

    boolean existsByPokemonAndUser(Pokemon pokemon, Users user);
}
