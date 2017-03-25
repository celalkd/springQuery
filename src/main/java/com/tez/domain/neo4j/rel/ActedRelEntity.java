/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.domain.neo4j.rel;

import com.tez.domain.neo4j.node.MovieNeo4jNode;
import com.tez.domain.neo4j.node.StarNeo4jNode;
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
@RelationshipEntity(type = "acted")
public class ActedRelEntity {

    @GraphId
    private Long id;

    @StartNode
    private StarNeo4jNode star;

    @EndNode
    private MovieNeo4jNode movie;
    
    public ActedRelEntity(Long id, StarNeo4jNode star, MovieNeo4jNode movie) {
        this.id = id;
        this.star = star;
        this.movie = movie;
    }
    
    public ActedRelEntity(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StarNeo4jNode getStar() {
        return star;
    }

    public void setStar(StarNeo4jNode star) {
        this.star = star;
    }

    
    public MovieNeo4jNode getMovie() {
        return movie;
    }

    public void setMovie(MovieNeo4jNode movie) {
        this.movie = movie;
    }
    

    
}
