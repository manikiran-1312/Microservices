package com.example.MovieInfoService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MovieInfoService.Model.MovieInfo;

@RestController
@RequestMapping("/movieinfo")
public class MovieInfoController {

	@RequestMapping("/{movieid}")
	public MovieInfo getMovieInfo(@PathVariable("movieid") String movieid) {
		return new MovieInfo(movieid, "Good Movie");
	}
	@GetMapping("/hello")
	public String getMessage() {
		return "This is Micro services World";
	}

}
