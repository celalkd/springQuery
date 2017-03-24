/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.controller;

import com.tez.repo.MovieNeo4jService;
import com.tez.domain.MovieNeo4jNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
    
    
    @RequestMapping(value="/neo4j/byTitle/{title}", method = RequestMethod.GET)
    public MovieNeo4jNode getMovieNodeByTitle(@PathVariable("title") String title){
        return  movieNeo4jService.getByTitle(title);
    }
    @RequestMapping(value="/neo4j/byMovieId/{movieId}", method = RequestMethod.GET)
    public MovieNeo4jNode getMovieNodeByMovieId(@PathVariable("movieId") String movieId){
        return  movieNeo4jService.getByMovieId(movieId);
    }
    
    
}
