package com.sparta.minipro;

import com.sparta.minipro.pokemon.PokemonService;
import com.sparta.minipro.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class Controller {
    private final PokemonRepository pokemonRepository;
    private final PokemonService pokemonService;

    @GetMapping("/data")
    public String upload() throws IOException {
        return pokemonService.data();
    }
}
