package com.wildcodeschool.hackloween.model;

public class Movie {
	
	private int id;
	private String title;
	private int year;
	private String director;
	private String country;
	private String posterUrl;
	private String createdAt;
	private String updatedAt;
	/*
	 {"id":3,"title":"Scream_2",
	 "director":"Wes_Craven","year":1997,"country":"United_States",
	 "posterUrl":"https://nsa40.casimages.com/img/2019/10/07/191007035836534706.jpg",
	 "createdAt":"2019-10-27T08:55:47.432Z","updatedAt":"2019-10-27T08:55:47.432Z"}
	 */
	
    public Movie() {
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title.replaceAll("_"," ");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getCountry() {
		return country.replaceAll("_"," ");
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getDirector() {
		return director.replaceAll("_"," ");
	}

	public void setDirector(String director) {
		this.director = director;
	}

}
