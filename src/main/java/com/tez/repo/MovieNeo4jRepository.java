/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.repo;

import com.tez.domain.neo4j.rel.ActedRelEntity;
import com.tez.domain.neo4j.rel.DirectedRelEntity;
import com.tez.domain.neo4j.rel.GenredRelEntity;
import com.tez.domain.neo4j.node.MovieNeo4jNode;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author celalkd
 */

public interface MovieNeo4jRepository extends GraphRepository<MovieNeo4jNode>{
    
    MovieNeo4jNode findByTitle(String title);
    MovieNeo4jNode findByMovieId(String movieId);
    
    @Query("MATCH (m:Movie)-[d:directed]-(user) WHERE id(m)={movie} return d")
    List<DirectedRelEntity> getDirector(@Param("movie") MovieNeo4jNode movie);
    
    @Query("MATCH (m:Movie)-[a:acted]-(user) WHERE id(m)={movie} return a")
    List<ActedRelEntity> getStar(@Param("movie") MovieNeo4jNode movie);
    
    @Query("MATCH (m:Movie)-[g:in_genre]-(user) WHERE id(m)={movie} return g")
    List<GenredRelEntity> getGenre(@Param("movie") MovieNeo4jNode movie);
    
}
