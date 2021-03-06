/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.controller;

import com.tez.domain.neo4j.node.DirectorNeo4jNode;
import com.tez.service.MovieNeo4jService;
import com.tez.domain.neo4j.node.MovieNeo4jNode;
import com.tez.domain.neo4j.rel.DirectedRelEntity;
import com.tez.service.DirectorNeo4jService;
import java.util.ArrayList;
import java.util.List;
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
public class DirectorNeo4jController {
    
    @Autowired DirectorNeo4jService directorService;
    
    
    @RequestMapping(value="/director/byName/{name}", method = RequestMethod.GET)
    public DirectorNeo4jNode getDirectorByName(@PathVariable("name") String name){
        return  directorService.getByName(name);
    }
    
//    @RequestMapping(value="/director/movies/{name}<>{title}", method = RequestMethod.GET)
//    public List<MovieNeo4jNode> getRelatedMovies(@PathVariable("name") String name,@PathVariable("title") String title){
//        
//        
//        List<DirectedRelEntity> rel_list = directorService.getOtherDirectedMovies(name, title);
//        
//        List<MovieNeo4jNode> movie_list = new ArrayList();
//        
//        for(DirectedRelEntity rel : rel_list){
//            movie_list.add(rel.getMovie());
//        }
//        
//        return movie_list;
//    }
    
    
    
    
}
