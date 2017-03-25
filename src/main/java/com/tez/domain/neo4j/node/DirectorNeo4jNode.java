/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.domain.neo4j.node;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 *
 * @author celalkd
 */
@NodeEntity(label= "Director")
public class DirectorNeo4jNode {
    
    @GraphId private Long id;
    private String name;
    
    public DirectorNeo4jNode(){
        
    }
    
    @Relationship(type = "directed")
    private List<MovieNeo4jNode> movies = new ArrayList<>();
    
    
    
    //GETTER_SETTER
    public DirectorNeo4jNode(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
