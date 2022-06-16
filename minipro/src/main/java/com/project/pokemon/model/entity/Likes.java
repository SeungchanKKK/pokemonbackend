package com.project.pokemon.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "pokemonId")
    private Pokemon pokemon;

    public Likes(Users user, Pokemon pokemon){
        this.user = user;
        this.pokemon = pokemon;
    }
}

