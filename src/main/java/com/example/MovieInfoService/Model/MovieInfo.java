package com.example.MovieInfoService.Model;

public class MovieInfo {

	private String movieid;
	private String moviename;

	public MovieInfo() {
		super();
		
	}

	public MovieInfo(String movieid, String moviename) {
		super();
		this.movieid = movieid;
		this.moviename = moviename;
	}

	public String getMovieid() {
		return movieid;
	}

	public void setMovieid(String movieid) {
		this.movieid = movieid;
	}

	public String getMoviename() {
		return moviename;
	}

	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}

}
