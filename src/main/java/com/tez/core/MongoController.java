package com.tez.core;

import com.tez.config.SpringMongoConfig;
import com.tez.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;//
import org.springframework.context.annotation.AnnotationConfigApplicationContext;//
import org.springframework.data.mongodb.core.MongoOperations;//
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController//
public class MongoController {
    
    @RequestMapping("/find/byTitle")//hhtp'ye bu uzantı yazılınca çalışacak olan methodun implementasyonu
    public Movie findByTitle(@RequestParam(value="title", defaultValue="Pulp Fiction") String title) {
       
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);//
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");//
        Query query = new Query();
        
        query.addCriteria(Criteria.where("title").is(title));
        Movie movie = mongoOperation.findOne(query, Movie.class);
        
        return movie;
    }
    @RequestMapping("/find/byId")//hhtp'ye bu uzantı yazılınca çalışacak olan methodun implementasyonu
    public Movie findById(@RequestParam(value="id", defaultValue="0") int id) {
       
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);//
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");//
        Query query = new Query();
        
        query.addCriteria(Criteria.where("_id").is(id));
        Movie movie = mongoOperation.findOne(query, Movie.class);
        
        return movie;
    }
    @RequestMapping("/find/byTags")//hhtp'ye bu uzantı yazılınca çalışacak olan methodun implementasyonu
    public List<Movie> findByTags(@RequestParam(value="director", required=false) String director,  
                                    @RequestParam(value="yearMin", required=false) Integer yearMin,
                                    @RequestParam(value="rating", required=false) Double rating,
                                    @RequestParam(value="yearMax", required=false) Integer yearMax,
                                    @RequestParam(value="genre", required=false) List<String> genre,
                                    @RequestParam(value="starring", required=false) List<String> starring)

    {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);//
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");//
        
        Criteria criteria = new Criteria();
        ArrayList<Criteria> criterias = new ArrayList();
        
        if(director!=null)
            criterias.add(Criteria.where("director").is(director));
        
        if(yearMin!=null && yearMax!=null)
            criterias.add(Criteria.where("year").gte(yearMin).lte(yearMax));
        else if(yearMin!=null)
            criterias.add(Criteria.where("year").gte(yearMin));
        else if(yearMax!=null)
            criterias.add(Criteria.where("year").lte(yearMax));
        
        if(rating!=null)
             criterias.add(Criteria.where("rating").gte(rating));
        
        if(starring!=null)
            criterias.add(Criteria.where("starring").all(starring));
       
        if(genre!=null)
            criterias.add(Criteria.where("genre").all(genre));
       
        
        
        Criteria[] criteriaArray = criterias.toArray(new Criteria[criterias.size()]);
        criteria = criteria.andOperator(criteriaArray);
        
        Query query = new Query().addCriteria(criteria);
        System.out.println(query.toString());
        return mongoOperation.find(query, Movie.class);

    }
}
