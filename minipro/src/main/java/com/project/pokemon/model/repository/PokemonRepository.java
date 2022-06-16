package com.project.pokemon.model.repository;

import com.project.pokemon.model.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    List<Pokemon> findAllByOrderById();
    List<Pokemon> findPokemonByName(String name);
}
