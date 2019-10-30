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
	private static boolean hasWinRound1 = false;
	private static boolean hasWinRound2 = false;
	private static boolean hasWinRound3 = false;
	private static boolean hasWinRound4 = false;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/quizz1")
    public String quizz1() {
        return "quizz1";
    }
    
    @GetMapping("/answerQuizz1")
    public String answerQuizz1(Model model,
    		@RequestParam(value="answer1", required=false) String answer1,
    		@RequestParam(value="answer2", required=false) String answer2,
    		@RequestParam(value="answer3", required=false) String answer3) {
    	
    	if (answer1.equals("2") && answer2.equals("1") && answer3.equals("2")) {
    		hasWinRound1 = true;
    		String response= "You win !";
    		model.addAttribute("response", response);
    		return "win";
    	} else {
    		String response= "You loose !";
    		model.addAttribute("response", response);
    		return "loose";
    	}   	
    	
       
    }
    
    
    @GetMapping("/test")
    public String test() {
        return "test";
    }
    
   
    
    @GetMapping("/loose")
    public String loose(Model model) {
    	if (!hasWinRound1) {
    		String response= "You loose at round 2!";
    		model.addAttribute("response", response);
    		return "test2";
    	}
    	else if (hasWinRound1 && !hasWinRound2) {
    		String response= "You loose at round 2!";
    		model.addAttribute("response", response);
    		return "test2";
    	} else if (hasWinRound1 && hasWinRound2 && !hasWinRound3) {
    		String response= "You loose at round 3!";
    		model.addAttribute("response", response);
    		return "test2";
    	} else if (hasWinRound1 && hasWinRound2 && !hasWinRound3 && !hasWinRound4) {
    		String response= "You loose at round 4!";
    		model.addAttribute("response", response);
    		return "test2";
    	}
    	return "loose";
    }
    
    @GetMapping("/win")
    public String win() {
        return "win";
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
