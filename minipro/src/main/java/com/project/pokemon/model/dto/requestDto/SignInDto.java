package com.project.pokemon.model.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class SignInDto {
    private String email;
    private String pw;
}
