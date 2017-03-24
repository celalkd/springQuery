/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.repo;

import com.tez.domain.MovieNeo4jNode;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *
 * @author celalkd
 */

public interface MovieNeo4jRepository extends GraphRepository<MovieNeo4jNode>{
    
    MovieNeo4jNode findByTitle(String title);
    MovieNeo4jNode findByMovieId(String movieId);
    
    
    
}
