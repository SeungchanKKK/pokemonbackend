package com.sparta.minipro.repository;

import com.sparta.minipro.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

}
