/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.service;

import com.tez.domain.MovieNeo4jNode;
import com.tez.repo.MovieNeo4jRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author celalkd
 */

@Service
public class MovieNeo4jService {
    
    @Autowired MovieNeo4jRepository movieRepo;
    
    public MovieNeo4jNode getByTitle(String title){
        return movieRepo.findByTitle(title);
    }
    
    public MovieNeo4jNode getByMovieId(String movieId){
        return movieRepo.findByMovieId(movieId);
    }
    
    
}
