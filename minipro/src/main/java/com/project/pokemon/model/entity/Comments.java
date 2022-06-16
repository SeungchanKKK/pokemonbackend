package com.project.pokemon.model.entity;

import com.project.pokemon.model.dto.requestDto.CommentsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Comments extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "pokemonId", nullable = false)
    private Pokemon pokemon;

    public Comments(CommentsDto commentsDto, Users user, Pokemon pokemon) {
        this.comments = commentsDto.getComments();
        this.users = user;
        this.pokemon = pokemon;
    }
}
