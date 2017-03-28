/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.service;

import com.tez.domain.neo4j.node.DirectorNeo4jNode;
import com.tez.domain.neo4j.node.MovieNeo4jNode;
import com.tez.domain.neo4j.rel.DirectedRelEntity;
import com.tez.repo.DirectorNeo4jRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author celalkd
 */

@Service
public class DirectorNeo4jService {
    
    @Autowired DirectorNeo4jRepository directorRepo;
    
    public DirectorNeo4jNode getByName(String name){
        return directorRepo.findByName(name);
    }
//    public List<DirectedRelEntity> getOtherDirectedMovies(String director, String movie){
//        
//        return directorRepo.getOtherDirectedMovies(director,movie );
//    }
    
}
