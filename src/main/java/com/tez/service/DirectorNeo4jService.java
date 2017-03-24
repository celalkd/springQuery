/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.service;

import com.tez.domain.DirectorNeo4jNode;
import com.tez.repo.DirectorNeo4jRepository;
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
    
    
}
