package com.tez.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tez.config.SpringMongoConfig;
import java.util.ArrayList;
import java.util.List;
import com.tez.domain.MovieMongoDB;
import org.apache.commons.lang.WordUtils;
import org.springframework.context.ApplicationContext;//
import org.springframework.context.annotation.AnnotationConfigApplicationContext;//
import org.springframework.data.mongodb.core.MongoOperations;//
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoController {
    
    ApplicationContext ctx;
    MongoOperations mongoOperation;
    
    public MongoController(){
        //via Annotation
        this.ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        this.mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        
        //via XML
        //this.ctx = new GenericXmlApplicationContext("SpringConfig.xml");
        //this.mongoOperation = (MongoOperations) ctx.getBean("mongoBean");
        
    }
   
    @RequestMapping("/find/byTitle")
    public MovieMongoDB findByTitle(@RequestParam(value="title", defaultValue="Pulp Fiction") String title) {
       
        Query query = new Query();
        //title = WordUtils.capitalize(title);
        query.addCriteria(Criteria.where("title").regex(title));
        MovieMongoDB movie = mongoOperation.findOne(query, MovieMongoDB.class);
        
        
        return movie;
    }
    @RequestMapping("/find/byId")
    public MovieMongoDB findById(@RequestParam(value="id", defaultValue="0") int id) {
       
        Query query = new Query();
        
        query.addCriteria(Criteria.where("_id").is(id));
        MovieMongoDB movie = mongoOperation.findOne(query, MovieMongoDB.class);
        
        return movie;
    }
    
    @RequestMapping("/find/byTags")
    public List<MovieMongoDB> findByTags(@RequestParam(value="director", required=false) String director,  
                                    @RequestParam(value="yearMin", required=false) Integer yearMin,
                                    @RequestParam(value="rating", required=false) Double rating,
                                    @RequestParam(value="yearMax", required=false) Integer yearMax,
                                    @RequestParam(value="genre", required=false) List<String> genre,
                                    @RequestParam(value="starring", required=false) List<String> starring,
                                    @RequestParam(value="titleRegex", required=false) String titleRegex,
                                    @RequestParam(value="notstarring", required=false) List<String> notstarring)

    {
        Criteria criteria = new Criteria();
        ArrayList<Criteria> criterias = new ArrayList();
        
        if(titleRegex!=null){
            titleRegex = WordUtils.capitalize(titleRegex);
            criterias.add(Criteria.where("title").regex("^"+titleRegex));            
        }
        if(director!=null){
            director = WordUtils.capitalize(director);
            criterias.add(Criteria.where("director").is(director));
        }
        
        if(yearMin!=null && yearMax!=null)
            criterias.add(Criteria.where("year").gte(yearMin).lte(yearMax));
        else if(yearMin!=null)
            criterias.add(Criteria.where("year").gte(yearMin));
        else if(yearMax!=null)
            criterias.add(Criteria.where("year").lte(yearMax));
        
        if(rating!=null)
             criterias.add(Criteria.where("rating").gte(rating));
        
        if(starring!=null){
            for(String star : starring){
                star = WordUtils.capitalize(star);
            }
            criterias.add(Criteria.where("starring").all(starring));
        }
        if(notstarring!=null){
            for(String star : notstarring){
                star = WordUtils.capitalize(star);
            }
            criterias.add(Criteria.where("starring").nin(notstarring));
        }
       
        if(genre!=null){
            for(String gen : genre){
                gen = WordUtils.capitalize(gen);
            }
            criterias.add(Criteria.where("genre").all(genre));
        }
        
        
        Criteria[] criteriaArray = criterias.toArray(new Criteria[criterias.size()]);
        criteria = criteria.andOperator(criteriaArray);
        
        Query query = new Query().addCriteria(criteria);
        System.out.println(query.toString());
        return mongoOperation.find(query, MovieMongoDB.class);

    }
    
    
}
