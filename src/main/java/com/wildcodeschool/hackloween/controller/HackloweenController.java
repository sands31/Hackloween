package com.wildcodeschool.hackloween.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildcodeschool.hackloween.model.Fighter;
import com.wildcodeschool.hackloween.model.Monster;
import com.wildcodeschool.hackloween.model.Movie;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class HackloweenController {
	
	private static final String HACKLOWEEN_URL = "https://hackathon-wild-hackoween.herokuapp.com";
	private Movie movie1 = movie(33);
	private Movie movie2 = movie(17);
	private Movie movie3 = movie(59);
	private Movie movie4 = movie(20);
	private Fighter you = new Fighter("You", 15);
	private Monster monster1 = monster(19);
	private Monster monster2 = monster(8);
	private Monster monster3 = monster(7);
	private Fighter fighter1 = new Fighter(monster1.getName(), monster1.getAttack());
	private Fighter fighter2 = new Fighter(monster2.getName(), monster2.getAttack());
	private Fighter fighter3 = new Fighter(monster3.getName(), monster3.getAttack());
	private Fighter[] fighters = {fighter1, fighter2, fighter3};
	private Monster[] monsters = {monster1, monster2, monster3};

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/quizz1")
    public String quizz1(Model model) {
    	model.addAttribute("movie1", movie1);
        return "quizz1";
    }
    
    @GetMapping("/answerQuizz1")
    public String answerQuizz1(Model model,
    		@RequestParam(value="answer1", required=false) String answer1,
    		@RequestParam(value="answer2", required=false) String answer2,
    		@RequestParam(value="answer3", required=false) String answer3) {
    	
    	if (answer1.equals("2") && answer2.equals("1") && answer3.equals("2")) {
    		String response= "You win !";
    		model.addAttribute("response", response);
    		return "quizz1won";
    	} else {
    		model.addAttribute("movie1", movie1);
        	model.addAttribute("movie2", movie2);
    		return "loose";
    	}   	       
    }
    
    @GetMapping("/fight1Choice")
    public String fight1Choice(Model model) {
    	model.addAttribute("monster1", monster1);
    	model.addAttribute("monster2", monster2);
    	model.addAttribute("monster3", monster3);
    	
    	
    	model.addAttribute("movie1", movie1);
    	model.addAttribute("movie2", movie2);
    	
    	return "fight1Choice";
    	
    }
    
    @GetMapping("/fight1/{num}")
    public String fight1(@PathVariable int num, Model model) {
    	//Combat
		ArrayList<String> fightInfos = new ArrayList<String>();
		String linkState3 = new String();
		fightInfos.add("The fight has begun !");
		while (fighters[num -1].getLife() > 0 && you.getLife() > 0) {
			fightInfos.add(fighters[num -1].takeHit(you));		
			if (fighters[num -1].getLife() > 0) {
				fightInfos.add(you.takeHit(fighters[num -1]));
			}
		}
		if (you.getLife() > 0) {
			fightInfos.add("You win !");
			linkState3  = "/quizz2";
		} else {
			fightInfos.add("You loose !");
			linkState3  = "/loose1";
		}
		
		model.addAttribute("linkState3", linkState3);
		model.addAttribute("fightResult", fightInfos);
    	model.addAttribute("monster", monsters[num -1]);
    	model.addAttribute("movie1", movie1);
    	model.addAttribute("movie2", movie2);
    	
        return "fight1";
    }
    
    @GetMapping("/quizz2")
    public String quizz2(Model model) {
    	model.addAttribute("movie1", movie1);
    	model.addAttribute("movie2", movie2);
    	model.addAttribute("movie3", movie3);
    	model.addAttribute("movie4", movie4);
        return "quizz2";
    }
    
    
    @GetMapping("/win")
    public String win(Model model) {
    	model.addAttribute("movie1", movie1);
    	model.addAttribute("movie2", movie2);
    	model.addAttribute("movie3", movie3);
    	model.addAttribute("movie4", movie4);
        return "win";
    }
    
    @GetMapping("/loose")
    public String loose(Model model) {
    	model.addAttribute("movie1", movie1);
    	model.addAttribute("movie2", movie2);
    	model.addAttribute("movie3", movie3);
    	model.addAttribute("movie4", movie4);
        return "loose";
    }
    
    @GetMapping("/loose1")
    public String loose1(Model model) {
    	model.addAttribute("movie1", movie1);
    	model.addAttribute("movie2", movie2);
    	model.addAttribute("movie3", movie3);
    	model.addAttribute("movie4", movie4);
        return "loose1";
    }
    
    
    @GetMapping("/loose2")
    public String loose2(Model model) {
    	model.addAttribute("movie1", movie1);
    	model.addAttribute("movie2", movie2);
    	model.addAttribute("movie3", movie3);
    	model.addAttribute("movie4", movie4);
        return "loose2";
    }
    
    @GetMapping("/loose3")
    public String loose3(Model model) {
    	model.addAttribute("movie1", movie1);
    	model.addAttribute("movie2", movie2);
    	model.addAttribute("movie3", movie3);
    	model.addAttribute("movie4", movie4);
        return "loose3";
    }
    
    
    @GetMapping("/test")
    public String test() {
        return "test";
    }
    
    
    @GetMapping("/movies")
    public String movies(Model model, @RequestParam int id) {
        model.addAttribute("movieInfos", movie(id));
        return "movies";
    }
    
    @GetMapping("/monsters")
    public String monsters(Model model, @RequestParam int id) {
        model.addAttribute("monsterInfos", monster(id));
        return "monsters";
    }
    
    
    public Movie movie(int id) { 	
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
    	return movieObject;
    }
    
    public Monster monster(int id) { 	
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
        return monsterObject;
    }
}
