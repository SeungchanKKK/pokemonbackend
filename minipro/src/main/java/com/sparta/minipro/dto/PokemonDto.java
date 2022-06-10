package com.sparta.minipro.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PokemonDto {
    private String name;
    private String num;
    private String imageUrl;
    private String element;
    private String desc;
}
