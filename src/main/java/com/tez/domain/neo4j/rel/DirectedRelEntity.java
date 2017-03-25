/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.domain.neo4j.rel;

import com.tez.domain.neo4j.node.MovieNeo4jNode;
import com.tez.domain.neo4j.node.DirectorNeo4jNode;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.Collection;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 *
 * @author celalkd
 */

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@RelationshipEntity(type = "directed")
public class DirectedRelEntity {

    @GraphId
    private Long id;

    @StartNode
    private DirectorNeo4jNode director;

    @EndNode
    private MovieNeo4jNode movie;
    
    public DirectedRelEntity(Long id, DirectorNeo4jNode director, MovieNeo4jNode movie) {
        this.id = id;
        this.director = director;
        this.movie = movie;
    }
    
    public DirectedRelEntity(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DirectorNeo4jNode getDirector() {
        return director;
    }

    public void setDirector(DirectorNeo4jNode director) {
        this.director = director;
    }

    public MovieNeo4jNode getMovie() {
        return movie;
    }

    public void setMovie(MovieNeo4jNode movie) {
        this.movie = movie;
    }
    

    
}
