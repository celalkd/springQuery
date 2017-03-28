/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.repo;

import com.tez.domain.neo4j.node.DirectorNeo4jNode;
import com.tez.domain.neo4j.node.MovieNeo4jNode;
import com.tez.domain.neo4j.rel.DirectedRelEntity;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author celalkd
 */

public interface DirectorNeo4jRepository extends GraphRepository<DirectorNeo4jNode>{
    
    DirectorNeo4jNode findByName(String name);
    
//    @Query("MATCH p =(:Director {name:{director}})-[:directed]-(m:Movie) WHERE m.title<>{movie} RETURN p")
//    List<DirectedRelEntity> getOtherDirectedMovies(@Param("director") String director,@Param("movie") String movie);
}
