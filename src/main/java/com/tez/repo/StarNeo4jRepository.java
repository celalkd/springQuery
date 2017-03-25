/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.repo;

import com.tez.domain.neo4j.node.DirectorNeo4jNode;
import com.tez.domain.neo4j.node.StarNeo4jNode;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *
 * @author celalkd
 */

public interface StarNeo4jRepository extends GraphRepository<StarNeo4jNode>{
    
    StarNeo4jNode findByName(String name);
}
