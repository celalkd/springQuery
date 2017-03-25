/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.controller;

import com.tez.domain.neo4j.node.DirectorNeo4jNode;
import com.tez.domain.neo4j.node.GenreNeo4jNode;
import com.tez.domain.neo4j.rel.ActedRelEntity;
import com.tez.domain.neo4j.rel.DirectedRelEntity;
import com.tez.domain.neo4j.rel.GenredRelEntity;
import com.tez.service.MovieNeo4jService;
import com.tez.domain.neo4j.node.MovieNeo4jNode;
import com.tez.domain.neo4j.node.StarNeo4jNode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author celalkd
 */

@RestController
public class MovieNeo4jController {
    
    @Autowired MovieNeo4jService movieNeo4jService;
    
    
    @RequestMapping(value="/neo4j/movie/byTitle/{title}", method = RequestMethod.GET)
    public MovieNeo4jNode getMovieNodeByTitle(@PathVariable("title") String title){
        return  movieNeo4jService.getByTitle(title);
    }
    
    @RequestMapping(value="/neo4j/movie/byMovieId/{movieId}", method = RequestMethod.GET)
    public MovieNeo4jNode getMovieNodeByMovieId(@PathVariable("movieId") String movieId){
        return  movieNeo4jService.getByMovieId(movieId);
    }
    
    @RequestMapping(value="/neo4j/directedRel/{title}", method = RequestMethod.GET)
    public List<DirectedRelEntity> getDirectedRelEntity(@PathVariable("title") String title){
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        return movieNeo4jService.getDirectorOfMovie(m);
    }
    
    @RequestMapping(value="/neo4j/actedRel/{title}", method = RequestMethod.GET)
    public List<ActedRelEntity> getActedRelEntity(@PathVariable("title") String title){
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        return movieNeo4jService.getCastOfMovie(m);
    }
    
    @RequestMapping(value="/neo4j/genredRel/{title}", method = RequestMethod.GET)
    public List<GenredRelEntity> getGenredRelEntity(@PathVariable("title") String title){
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        return movieNeo4jService.getGenreOfMovie(m);
    }
    
    @RequestMapping(value="/neo4j/director/{title}", method = RequestMethod.GET)
    public DirectorNeo4jNode getDirector(@PathVariable("title") String title){
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        DirectedRelEntity rel = movieNeo4jService.getDirectorOfMovie(m).get(0);
        return  rel.getDirector();
    }
    
    @RequestMapping(value="/neo4j/cast/{title}", method = RequestMethod.GET)
    public List<StarNeo4jNode> getCast(@PathVariable("title") String title){
        
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        List<ActedRelEntity> rel_list = movieNeo4jService.getCastOfMovie(m);
        
        List<StarNeo4jNode> cast = new ArrayList();
        
        for(ActedRelEntity rel : rel_list){
            cast.add(rel.getStar());
        }
        return cast;
    }
    
    @RequestMapping(value="/neo4j/genre/{title}", method = RequestMethod.GET)
    public List<GenreNeo4jNode> getGenres(@PathVariable("title") String title){
        
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        List<GenredRelEntity> rel_list = movieNeo4jService.getGenreOfMovie(m);
        
        List<GenreNeo4jNode> genres = new ArrayList();
        
        for(GenredRelEntity rel : rel_list){
            genres.add(rel.getGenre());
        }
        return genres;
    }
    
}
