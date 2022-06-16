package com.project.pokemon.model.repository;

import com.project.pokemon.model.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByPokemonIdOrderByCreatedAtDesc(Long pokemonId);
}
