package com.project.pokemon.model.dto.responseDto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginRespDto {
    boolean result;
    String errorMsg;
    String token;
    String refreshToken;

    public UserLoginRespDto(boolean result, String errorMsg) {
        this.result = result;
        this.errorMsg = errorMsg;
    }

    public UserLoginRespDto(boolean result, String token, String refreshToken, String errorMsg) {
        this.result = result;
        this.errorMsg = errorMsg;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}