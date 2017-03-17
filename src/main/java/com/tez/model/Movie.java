/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.model;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "moviesCollection")
public class Movie {
    
    @Id
    public int id;
    public String title;
    public String director;
    public int year;       
    public ArrayList<String> starring = new ArrayList();
    public ArrayList<String> genre = new ArrayList();
    public double rating;    
    public String wikiURL;
    public String vikiURL; 

    public Movie(int id, String title, String director, int year, double rating, String wikiURL, String vikiURL, ArrayList<String> starring, ArrayList<String> genre) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.wikiURL = wikiURL;
        this.vikiURL = vikiURL;
        this.starring = starring;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie{" + 
                "id=" + id + 
                ", title=" + title + 
                ", director=" + director + 
                ", year=" + year + 
                ", rating=" + rating + 
                ", wikiURL=" + wikiURL + 
                ", vikiURL=" + vikiURL + 
                ", starring=" + starring + 
                ", genre=" + genre + '}' +
                "\n";
    }
    
    
    
}
