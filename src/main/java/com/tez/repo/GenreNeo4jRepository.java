/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.repo;

import com.tez.domain.neo4j.node.DirectorNeo4jNode;
import com.tez.domain.neo4j.node.GenreNeo4jNode;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *
 * @author celalkd
 */

public interface GenreNeo4jRepository extends GraphRepository<GenreNeo4jNode>{
    
    GenreNeo4jNode findByName(String name);
}
