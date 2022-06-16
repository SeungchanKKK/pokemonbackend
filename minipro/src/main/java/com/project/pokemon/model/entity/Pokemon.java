package com.project.pokemon.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity
public class Pokemon {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String num;

    @Column(nullable = false)
    private String element;

    @Column
    private String info;

    @Column
    private Long likesCnt;

    public Pokemon(String pokemonnum,String img, String pokemonname, String ele,String ddesc) {
        this.name = pokemonname;
        this.imageUrl = img;
        this.num = pokemonnum;
        this.element = ele;
        this.info= ddesc;
    }
}