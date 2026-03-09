package com.ust.ms.movie.controller;

//http://localhost:9091/info/list/1001

import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ust.ms.movie.dto.Info;
import com.ust.ms.movie.dto.Movie;
import com.ust.ms.movie.dto.Rating;
@RestController
@RequestMapping("/info")
public class InfoServiceController {

	@Autowired
	RestTemplate rest;
	//http://localhost:9091/info/list/1001
	@RequestMapping("/list/{movid}")
	public List<Movie> getAllData(@PathVariable("movid") int  id){
		Rating rating=rest.getForObject("http://localhost:9092/rating/list", Rating.class);
		Info info=rest.getForObject("http://localhost:9093/cato/list", Info.class);
		
		return Collections.singletonList(new Movie(1001,info.getMovieName(),rating.getMovieRating()));
		
		
	}
	
}
