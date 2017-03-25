/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.repo;

import com.tez.domain.neo4j.node.DirectorNeo4jNode;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *
 * @author celalkd
 */

public interface DirectorNeo4jRepository extends GraphRepository<DirectorNeo4jNode>{
    
    DirectorNeo4jNode findByName(String name);
}
