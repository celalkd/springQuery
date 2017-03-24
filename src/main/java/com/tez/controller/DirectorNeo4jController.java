/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.controller;

import com.tez.domain.DirectorNeo4jNode;
import com.tez.service.MovieNeo4jService;
import com.tez.domain.MovieNeo4jNode;
import com.tez.service.DirectorNeo4jService;
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
    
    
    
    
}
