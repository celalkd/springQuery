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
import java.util.Collection;
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
    List<ActedRelEntity> getCast(@Param("movie") MovieNeo4jNode movie);
    
    @Query("MATCH (m:Movie)-[g:in_genre]-(user) WHERE id(m)={movie} return g")
    List<GenredRelEntity> getGenre(@Param("movie") MovieNeo4jNode movie);
    
    //RECOMMENDATION QUERIES "x filmi dışındaki filmlerle ilişkili olan director/genre/star"
    @Query("MATCH p =(:Director {name:{director}})-[:directed]-(m:Movie) WHERE m.title<>{movie} RETURN p")
    List<DirectedRelEntity> getMoviesWithSameDirector(@Param("director") String director,@Param("movie") String movie);
    
    @Query("MATCH p =(:Star {name:{star}})-[:acted]-(m:Movie) WHERE m.title<>{movie} RETURN p")
    List<ActedRelEntity> getMoviesWithSameStars(@Param("star") String director,@Param("movie") String movie);
    
    @Query("MATCH p =(:Genre {name:{genre}})-[:in_genre]-(m:Movie) WHERE m.title<>{movie} RETURN p")
    List<GenredRelEntity> getMoviesWithSameGenre(@Param("genre") String genre,@Param("movie") String movie);
    
    //D3
    @Query("MATCH (m:Movie)-[r:directed]-(a:Director) RETURN m,r,a LIMIT {limit}")
    Collection<MovieNeo4jNode> graph(@Param("limit") int limit);
    
    @Query("MATCH (m:Movie {title:{title}})-[r]-(a) RETURN m,r,a LIMIT {limit}")
    MovieNeo4jNode fullGraph_2(@Param("limit") int limit, @Param("title") String title);
    
    @Query("MATCH (m:Movie)-[r]-(a) WHERE m.title={title1} OR m.title={title2} OR m.title={title3} OR m.title={title4} RETURN m,r,a LIMIT {limit}")
    Collection<MovieNeo4jNode> fullGraph(@Param("limit") int limit,
                                @Param("title1") String title1,
                                    @Param("title2") String title2,
                                            @Param("title3") String title3,
                                                @Param("title4") String title4);
}
