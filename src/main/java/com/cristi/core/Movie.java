package com.cristi.core;


import java.util.Collection;

public class Movie {

    private final String id;
    private final String name;
    //Collection<Genre> genre;
    private final double rating;

    public double getRating() {
		return rating;
	}

	public Movie(String id, String name, double rating) {
        this.id = id;
        this.name = name;
        this.rating=rating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
