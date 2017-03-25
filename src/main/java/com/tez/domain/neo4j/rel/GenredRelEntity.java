/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.domain.neo4j.rel;

import com.tez.domain.neo4j.node.MovieNeo4jNode;
import com.tez.domain.neo4j.node.GenreNeo4jNode;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 *
 * @author celalkd
 */

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@RelationshipEntity(type = "in_genre")
public class GenredRelEntity {

    @GraphId
    private Long id;
    
    
    @StartNode
    private GenreNeo4jNode genre;
    
    @EndNode
    private MovieNeo4jNode movie;

    
    
    public GenredRelEntity(Long id, GenreNeo4jNode genre, MovieNeo4jNode movie) {
        this.id = id;
        this.genre = genre;
        this.movie = movie;
    }
    
    public GenredRelEntity(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenreNeo4jNode getGenre() {
        return genre;
    }

    public void setGenre(GenreNeo4jNode genre) {
        this.genre = genre;
    }

    public MovieNeo4jNode getMovie() {
        return movie;
    }

    public void setMovie(MovieNeo4jNode movie) {
        this.movie = movie;
    }
    

    
}
