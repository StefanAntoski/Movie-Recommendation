package com.example.antoski.movierecommendation;

/**
 * Created by krpet on 12.6.2016.
 */

public class Film {
    public String id;
    public String name;
    public int year;

    public Film() {

    }

    public Film(String id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    @Override
    public String toString() {
        return name + " (" + year +")";

    }
}
