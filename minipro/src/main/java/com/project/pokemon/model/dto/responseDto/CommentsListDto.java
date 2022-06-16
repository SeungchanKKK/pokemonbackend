package com.project.pokemon.model.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class CommentsListDto {
    String comments;
    String nickname;
    LocalDateTime createdAt;
}
