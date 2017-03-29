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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author celalkd
 */

@RestController
public class MovieNeo4jController {
    
    @Autowired MovieNeo4jService movieNeo4jService;
    
    
    @RequestMapping(value="/neo4j/movie/byTitle/{title}", method = RequestMethod.GET)
    public MovieNeo4jNode getByTitle(@PathVariable("title") String title){
        return  movieNeo4jService.getByTitle(title);
    }
    
    @RequestMapping(value="/neo4j/movie/byMovieId/{movieId}", method = RequestMethod.GET)
    public MovieNeo4jNode getByMovieId(@PathVariable("movieId") String movieId){
        return  movieNeo4jService.getByMovieId(movieId);
    }
    
    @RequestMapping(value="/neo4j/directedRel/{title}", method = RequestMethod.GET)
    public List<DirectedRelEntity> getDirectedRelEntity(@PathVariable("title") String title){
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        return movieNeo4jService.getDirector(m);
    }
    
    @RequestMapping(value="/neo4j/actedRel/{title}", method = RequestMethod.GET)
    public List<ActedRelEntity> getActedRelEntity(@PathVariable("title") String title){
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        return movieNeo4jService.getCast(m);
    }
    
    @RequestMapping(value="/neo4j/genredRel/{title}", method = RequestMethod.GET)
    public List<GenredRelEntity> getGenredRelEntity(@PathVariable("title") String title){
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        return movieNeo4jService.getGenreOfMovie(m);
    }
    
    @RequestMapping(value="/neo4j/director/{title}", method = RequestMethod.GET)
    public DirectorNeo4jNode getDirector(@PathVariable("title") String title){
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        DirectedRelEntity rel = movieNeo4jService.getDirector(m).get(0);
        return  rel.getDirector();
    }
    
    @RequestMapping(value="/neo4j/cast/{title}", method = RequestMethod.GET)
    public List<StarNeo4jNode> getCast(@PathVariable("title") String title){
        
        MovieNeo4jNode m =  movieNeo4jService.getByTitle(title);
        List<ActedRelEntity> rel_list = movieNeo4jService.getCast(m);
        
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
    
    //RECOMMENDATION METHODS    
    final static int genrePOINTS = 150;
    final static int directorPOINTS = 100;
    final static int castPOINTS = 50;   
    
    List<Adjacency> global_adj_list;
    
    @RequestMapping(value="/recommendation/{title}", method = RequestMethod.GET)
    public Map<String, Object> getRecommendation(@PathVariable("title") String title){
        
        global_adj_list = new ArrayList();
        
        this.getMoviesWithSameDirector(title);
        this.getMoviesWithSameStar(title);
        this.getMoviesWithSameGenre(title);
        
        Collections.sort(global_adj_list, new CustomComparator());
        
        List<String> result = new ArrayList<>();
        
        int i=0;
        for(Adjacency a : global_adj_list){
            if(i<3){
                MovieNeo4jNode movie = movieNeo4jService.getByMovieId(a.getMovieId());
                result.add(movie.getTitle());
                i++;
            }
            else
                break;
        }
        return this.graph(title, result.get(0), result.get(1), result.get(2));
        
        
    }
    
    @RequestMapping(value="/same_director/{title}", method = RequestMethod.GET)
    public List<Adjacency> getMoviesWithSameDirector(@PathVariable("title") String title){
        
        //List<Adjacency> adj_list = new ArrayList<>();
        MovieNeo4jNode movie = movieNeo4jService.getByTitle(title);
        DirectedRelEntity directedRel = movieNeo4jService.getDirector(movie).get(0);
        
        String directorName = directedRel.getDirector().getName();
        List<DirectedRelEntity> rel_list = movieNeo4jService.getMoviesWithSameDirector(directorName, title);
                
        for(DirectedRelEntity rel : rel_list){            
            String movieID = rel.getMovie().getMovieId();            
            updateAdjacencyList(global_adj_list, movieID, directorPOINTS);
        }        
        return global_adj_list;
    }
    
    @RequestMapping(value="/same_cast/{title}", method = RequestMethod.GET)
    public List<Adjacency> getMoviesWithSameStar(@PathVariable("title") String title){
        
        //List<Adjacency> adj_list = new ArrayList<>();
        MovieNeo4jNode movie = movieNeo4jService.getByTitle(title);
        List<ActedRelEntity> cast = movieNeo4jService.getCast(movie);
        
        for(ActedRelEntity actedRel : cast){
            String name = actedRel.getStar().getName();
            List<ActedRelEntity> rel_list = movieNeo4jService.getMoviesWithSameStar(name, title);
                
            for(ActedRelEntity rel : rel_list){            
                String movieID = rel.getMovie().getMovieId();            
                updateAdjacencyList(global_adj_list, movieID, castPOINTS);
            }        
        }
        
        return global_adj_list;
    }    
    @RequestMapping(value="/same_genre/{title}", method = RequestMethod.GET)
    public List<Adjacency> getMoviesWithSameGenre(@PathVariable("title") String title){
        
        //List<Adjacency> adj_list = new ArrayList<>();
        MovieNeo4jNode movie = movieNeo4jService.getByTitle(title);
        List<GenredRelEntity> genres = movieNeo4jService.getGenreOfMovie(movie);
        
        for(GenredRelEntity genredRel : genres){
            String name = genredRel.getGenre().getName();
            List<GenredRelEntity> rel_list = movieNeo4jService.getMoviesWithSameGenre(name, title);
                
            for(GenredRelEntity rel : rel_list){            
                String movieID = rel.getMovie().getMovieId();            
                updateAdjacencyList(global_adj_list, movieID, genrePOINTS);
            }        
        }
        
        return global_adj_list;
    }    
    
    
    //HELPER CLASSS for Recommendations
    public class Adjacency{
        String mov;
        int points;

        public Adjacency(String mov, int points) {
            this.mov = mov;
            this.points = points;
        }
        public Adjacency(String mov){
            this.mov = mov;
            this.points = 0;
        }

        public String getMovieId() {
            return mov;
        }

        public void setMovieId(String mov) {
            this.mov = mov;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }
        
        
        public int searchIn(List<Adjacency> adj_list)
        {
            for(Adjacency adj : adj_list){
                if(this.mov.equals(adj.mov))
                    return adj_list.indexOf(adj);
            }
            return -1;
        }
        
    }
    public void updateAdjacencyList(List<Adjacency> adj_list, String movieID, int addPoints){
            Adjacency adj = new Adjacency(movieID);            
            int index = adj.searchIn(adj_list);
            
            if(index==-1){
                adj.setPoints(adj.getPoints()+addPoints);
                adj_list.add(adj);
            }
            else{
                int points = adj_list.get(index).getPoints();
                adj_list.get(index).setPoints(points+addPoints);
            }
    }
    public class CustomComparator implements Comparator<Adjacency> {
        @Override
        public int compare(Adjacency o1, Adjacency o2) {
            Integer f1 = o1.getPoints();
            Integer f2 = o2.getPoints();

            return f2.compareTo(f1);
        }
        
    }    
    
    //D3
    @RequestMapping("/graph/{title1},{title2},{title3},{title4}")
	public Map<String, Object> graph(@PathVariable("title1") String title1,
                @PathVariable("title2") String title2,
                @PathVariable("title3") String title3,
                @PathVariable("title4") String title4) {
		return movieNeo4jService.graph(10000,title1, title2,title3,title4);
	}
}

