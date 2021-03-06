/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.domain;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "moviesCollection")
public class MovieMongoDB {
    
    @Id
    public int id;
    public String title;
    public String başlık;
    public String director;
    public int year;       
    public ArrayList<String> starring = new ArrayList();
    public ArrayList<String> genre = new ArrayList();
    public double rating;    
    public String wikiURL;
    public String vikiURL; 
    public String poster;
    public String plot;
    public String konu;

    public MovieMongoDB(int id, String title, String başlık, String director, int year, double rating, String wikiURL, String vikiURL,
                    ArrayList<String> starring, ArrayList<String> genre, String poster, String plot, String konu) {
        this.id = id;
        this.title = title;
        this.başlık = başlık;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.wikiURL = wikiURL;
        this.vikiURL = vikiURL;
        this.starring = starring;
        this.genre = genre;
        this.poster = poster;
        this.plot = plot;
        this.konu = konu;
    }
    
}
