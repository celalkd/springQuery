/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.domain.neo4j.node;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tez.domain.neo4j.rel.ActedRelEntity;
import com.tez.domain.neo4j.rel.DirectedRelEntity;
import com.tez.domain.neo4j.rel.GenredRelEntity;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import java.util.*;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity(label= "Movie")
public class MovieNeo4jNode {
    
    @GraphId
    private Long id;

    public MovieNeo4jNode(Long id, String movieId, String title, String url) {
        this.id = id;
        this.movieId = movieId;
        this.title = title;
        this.url = url;
    }

    public MovieNeo4jNode() {
    }
    
    @Relationship(type = "directed", direction = Relationship.INCOMING )
    private List<DirectedRelEntity> director = new ArrayList<>();
    
    @Relationship(type = "acted", direction = Relationship.INCOMING)
    private List<ActedRelEntity> cast = new ArrayList<>();
    
    @Relationship(type = "in_genre", direction = Relationship.INCOMING)
    private List<GenredRelEntity> genreList = new ArrayList<>();
    
    //GETTER_SETTER
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    private String movieId;
    private String title;
    private String url;
    
    
}