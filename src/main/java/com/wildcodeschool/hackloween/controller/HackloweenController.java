package com.wildcodeschool.hackloween.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildcodeschool.hackloween.model.Monster;
import com.wildcodeschool.hackloween.model.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class HackloweenController {
	
	private static final String HACKLOWEEN_URL = "https://hackathon-wild-hackoween.herokuapp.com";

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/movies")
    public String movies(Model model, @RequestParam Long id) {

        WebClient webClient = WebClient.create(HACKLOWEEN_URL);
        Mono<String> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movies/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(String.class);

        String response = call.block();
        

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode actualObj = null;
        try {
        	actualObj = objectMapper.readTree(response);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        response =  actualObj.get("movie").toString();
        
        Movie movieObject = null;
        try {
        	movieObject = objectMapper.readValue(response, Movie.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("movieInfos", movieObject);

        return "movies";
    }
    
    @GetMapping("/monsters")
    public String monsters(Model model, @RequestParam Long id) {

        WebClient webClient = WebClient.create(HACKLOWEEN_URL);
        Mono<String> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/monsters/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(String.class);

        String response = call.block();
        

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode actualObj = null;
        try {
        	actualObj = objectMapper.readTree(response);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        response =  actualObj.get("monster").toString();
        
        Monster monsterObject = null;
        try {
        	monsterObject = objectMapper.readValue(response, Monster.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("monsterInfos", monsterObject);

        return "monsters";
    }
}
