package com.sparta.minipro.pokemon;


import com.sparta.minipro.dto.PokemonDto;
import com.sparta.minipro.model.Pokemon;
import com.sparta.minipro.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PokemonService {

    static PokemonRepository pokemonRepository;

    @SneakyThrows
    public static void main(String[] args) {

        for(int i =1; i <100 ; i++){
        String crawlingURL = "https://pokemonkorea.co.kr/pokedex/view/" +i ;

        Connection conn = Jsoup.connect(crawlingURL);
        Document document = conn.get();

        Elements pokemoncard = document.getElementsByClass("bx-content row");

            for (Element pokenum : pokemoncard) {

                List<String> pokemonnamenum= Arrays.asList(pokenum.getElementsByTag("h3").text().split(" "));

                String pokemonnum = pokemonnamenum.get(1);
                String pokemonname = pokemonnamenum.get(2);
                    for (Element pokeimage : pokemoncard) {
                        String img = String.valueOf(pokeimage.getElementsByTag("img").get(0).attr("src"));
                        for(Element pokedesc : pokemoncard){
                            String ddesc = String.valueOf(pokedesc.getElementsByClass("para descript").text());
                            String ele = pokeimage.getElementsByClass("img-type").text();

                            Pokemon pokemon = new Pokemon (pokemonnum,img,pokemonname,ddesc,ele);

                            pokemonRepository.save(pokemon);

                            System.out.println(pokemonnum);
                            System.out.println(img);
                            System.out.println(pokemonname);
                            System.out.println(ddesc);
                            System.out.println(ele);


                        }
                    }
                }
            }

    }
}

