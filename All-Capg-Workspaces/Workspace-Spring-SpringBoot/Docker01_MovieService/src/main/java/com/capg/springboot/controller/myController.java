package com.capg.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.springboot.dto.movieDTO;

@RestController
@RequestMapping("/movie")
public class myController {
	@RequestMapping("/cat")
	public movieDTO myMovies() {
		return new movieDTO("RRR",5000);
	}
}
