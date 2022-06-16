package com.project.pokemon.Service;


import com.project.pokemon.model.dto.requestDto.SearchDto;
import com.project.pokemon.model.entity.Pokemon;
import com.project.pokemon.model.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    //크롤링
    public String data() throws IOException {

        for(int i =1; i <209 ; i++){
            String crawlingURL = "https://pokemonkorea.co.kr/pokedex/view/" +i ;

            Connection conn = Jsoup.connect(crawlingURL);
            Document document = conn.get();

            Elements pokemoncard = document.getElementsByClass("bx-content row");

            for (Element pokenum : pokemoncard) {

                List<String> pokemonnamenum= Arrays.asList(pokenum.getElementsByTag("h3").text().split(" "));

                String pokemonnum = pokemonnamenum.get(1);
                String pokemonname = pokemonnamenum.get(2);
                for (Element pokeimage : pokemoncard) {
                    String img = pokeimage.getElementsByTag("img").get(0).attr("src");
                    for(Element pokedesc : pokemoncard){
                        String ddesc = pokedesc.getElementsByClass("para descript").text();
                        String ele = pokeimage.getElementsByClass("img-type").text();

                        Pokemon pokemon = new Pokemon (pokemonnum,img,pokemonname,ele,ddesc);

                        pokemonRepository.save(pokemon);


                    }
                }
            }
        }
        return "업로드완료";
    }

    //검색기능
    public List<Pokemon> search(SearchDto searchDto) {
        return pokemonRepository.findPokemonByName(searchDto.getName());
    }
    //디테일페이지 로드
    public Pokemon detail( Long pokemonId) {
        return pokemonRepository.findById(pokemonId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지않는 포켓몬입니다!"));

    }
    //메인페이지 로드
    public List<Pokemon> load() {
        return pokemonRepository.findAllByOrderById();
    }
}

