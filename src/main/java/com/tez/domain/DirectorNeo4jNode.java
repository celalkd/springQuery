/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

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
