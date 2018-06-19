package com.el.jokes_mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.el.jokes_mvc.models.Joke;
import com.el.jokes_mvc.repositories.JokeRepository;

@Controller
public class JokesController {
	
	@Autowired
	private JokeRepository jokeRepository;

	public JokesController() {
		
	}
	
	@GetMapping("/")
	public ModelAndView calculator() {
		ModelAndView mv = new ModelAndView();
		List<Joke> jokes = jokeRepository.findAll();
		mv.addObject("jokes", jokes);
		mv.setViewName("read");
		return mv;
	}
	@GetMapping("/new_joke")
	public ModelAndView new_joke() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("new_joke");
		return mv;
	}
	
	@PostMapping("/new_joke")
	public ModelAndView create_joke(String joke, String punchline, int rating) {
		Joke newJoke = new Joke(joke, punchline, rating);
		jokeRepository.save(newJoke);
    	ModelAndView mv = new ModelAndView();

		mv.setViewName("redirect:/");

	    return mv;
    }

	@PostMapping("/delete_joke")
	public ModelAndView delete_joke(int jokeID) {

		//Joke joke = jokeRepository.getOne(jokeID);
		jokeRepository.delete(jokeID);
    	ModelAndView mv = new ModelAndView();

		mv.setViewName("redirect:/");

	    return mv;
    }
	
	@PostMapping("/update_joke")
	public ModelAndView update_joke(int jokeID) {
		ModelAndView mv = new ModelAndView();
		Joke update_joke = jokeRepository.findOne(jokeID);
		String joke = update_joke.getJoke();
		String punchline = update_joke.getPunchline();
		int rating = update_joke.getRating();
		mv.addObject("joke", joke);
		mv.addObject("punchline", punchline);
		mv.addObject("rating", rating);
		mv.addObject("id", jokeID);
		mv.setViewName("update_joke");
		return mv;
	}
	
	@PostMapping("/update")
	public ModelAndView update(String joke, String punchline, int rating, int id) {
		ModelAndView mv = new ModelAndView();
		Joke updated_joke = jokeRepository.findOne(id);
		updated_joke.setJoke(joke);
		updated_joke.setPunchline(punchline);
		updated_joke.setRating(rating);
		jokeRepository.save(updated_joke);
		mv.setViewName("redirect:/");
		return mv;
	}
}
