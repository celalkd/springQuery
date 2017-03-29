/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.service;

import com.tez.domain.neo4j.rel.ActedRelEntity;
import com.tez.domain.neo4j.rel.DirectedRelEntity;
import com.tez.domain.neo4j.node.DirectorNeo4jNode;
import com.tez.domain.neo4j.rel.GenredRelEntity;
import com.tez.domain.neo4j.node.MovieNeo4jNode;
import com.tez.repo.MovieNeo4jRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    public List<DirectedRelEntity> getDirector(MovieNeo4jNode movie){
        return movieRepo.getDirector(movie);
    }
    public List<ActedRelEntity> getCast(MovieNeo4jNode movie){
        return movieRepo.getCast(movie);
    }
    public List<GenredRelEntity> getGenreOfMovie(MovieNeo4jNode movie){
        return movieRepo.getGenre(movie);
    }
    
    
    //RECOMMENDATION QUERY KULLANAN SERVISLER
    public List<DirectedRelEntity> getMoviesWithSameDirector(String director, String movie){
        return movieRepo.getMoviesWithSameDirector(director,movie );
    }
    
    public List<ActedRelEntity> getMoviesWithSameStar(String star, String movie){
        return movieRepo.getMoviesWithSameStars(star,movie );
    }
    
    public List<GenredRelEntity> getMoviesWithSameGenre(String genre, String movie){
        return movieRepo.getMoviesWithSameGenre(genre,movie );
    }
    
    //D3
    private Map<String, Object> toD3Format(Collection<MovieNeo4jNode> movies) {
		List<Map<String, Object>> nodes = new ArrayList<>();                
		List<Map<String, Object>> rels = new ArrayList<>();
		int i = 0;
		Iterator<MovieNeo4jNode> result = movies.iterator();
		while (result.hasNext()) {
			MovieNeo4jNode movie = result.next();
//                        Map<String, Object> m = map("title", movie.getTitle(), "label", "Movie");
//                        System.out.println(m.toString());
			nodes.add(map("title", movie.getTitle(), "label", "Movie"));
                        
			int target = i;
			i++;
			for (DirectedRelEntity director : movieRepo.getDirector(movie)) {
				Map<String, Object> dMap = map("name", director.getDirector().getName(), "label", "Director");
				int source = nodes.indexOf(dMap);
				if (source == -1) {
					nodes.add(dMap);
					source = i++;
				}
				rels.add(map("source", source, "target", target));
			}
                        for (ActedRelEntity actor : movieRepo.getCast(movie)) {
				Map<String, Object> sMap = map("name", actor.getStar().getName(), "label", "Star");
                               
				int source = nodes.indexOf(sMap);
				if (source == -1) {
					nodes.add(sMap);
					source = i++;
				}
				rels.add(map("source", source, "target", target));
			}
                        for (GenredRelEntity genre : movieRepo.getGenre(movie)) {
				Map<String, Object> gMap = map("name", genre.getGenre().getName(), "label", "Genre");
				int source = nodes.indexOf(gMap);
				if (source == -1) {
					nodes.add(gMap);
					source = i++;
				}
				rels.add(map("source", source, "target", target));
			}
                        
		}
                
		return map("nodes", nodes, "links", rels);
	}


	private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}
	

        @Transactional(readOnly = true)
	public Map<String, Object>  graph(int limit, String title1,String title2,String title3) {
		Collection<MovieNeo4jNode> result = movieRepo.fullGraph(limit, title1, title2,title3);
		return toD3Format(result);
	}
}










//        private Map<String, Object> toD3Format(Collection<MovieNeo4jNode> movies) {
//		List<Map<String, Object>> nodes = new ArrayList<>();                
//		List<Map<String, Object>> rels = new ArrayList<>();
//		int i = 0;
//		Iterator<MovieNeo4jNode> result = movies.iterator();
//		while (result.hasNext()) {
//			
//                        MovieNeo4jNode movie = result.next();
//                        
//			nodes.add(map("title", movie.getTitle(), "label", "Movie"));
//                        
//			int target = i;
//			i++;
//			for (DirectedRelEntity director : movieRepo.getDirector(movie)) {
//				Map<String, Object> dMap = map("name", director.getDirector().getName(), "label", "Director");
//				int source = nodes.indexOf(dMap);
//				if (source == -1) {
//					nodes.add(dMap);
//					source = i++;
//				}
//				rels.add(map("source", source, "target", target));
//			}
//                        for (ActedRelEntity actor : movieRepo.getCast(movie)) {
//				Map<String, Object> sMap = map("name", actor.getStar().getName(), "label", "Star");
//				int source = nodes.indexOf(sMap);
//				if (source == -1) {
//					nodes.add(sMap);
//					source = i++;
//				}
//				rels.add(map("source", source, "target", target));
//			}
//		}
//		return map("nodes", nodes, "links", rels);
//	}
//        private Map<String, Object> toD3Format(MovieNeo4jNode movie) {
//		List<Map<String, Object>> nodes = new ArrayList<>();                
//		List<Map<String, Object>> rels = new ArrayList<>();
//		int i = 0;
//		
//		
//                        
//			nodes.add(map("title", movie.getTitle(), "label", "Movie"));
//                        
//			int target = i;
//			i++;
//			for (DirectedRelEntity director : movieRepo.getDirector(movie)) {
//				Map<String, Object> dMap = map("name", director.getDirector().getName(), "label", "Director");
//				int source = nodes.indexOf(dMap);
//				if (source == -1) {
//					nodes.add(dMap);
//					source = i++;
//				}
//				rels.add(map("source", source, "target", target));
//			}
//                        for (ActedRelEntity actor : movieRepo.getCast(movie)) {
//				Map<String, Object> sMap = map("name", actor.getStar().getName(), "label", "Star");
//				int source = nodes.indexOf(sMap);
//				if (source == -1) {
//					nodes.add(sMap);
//					source = i++;
//				}
//				rels.add(map("source", source, "target", target));
//			}
//                        for (GenredRelEntity genre : movieRepo.getGenre(movie)) {
//				Map<String, Object> gMap = map("name", genre.getGenre().getName(), "label", "Genre");
//				int source = nodes.indexOf(gMap);
//				if (source == -1) {
//					nodes.add(gMap);
//					source = i++;
//				}
//				rels.add(map("source", source, "target", target));
//			}
//		
//		return map("nodes", nodes, "links", rels);
//	}
//        @Transactional(readOnly = true)
//	public Map<String, Object>  graph(int limit) {
//		Collection<MovieNeo4jNode> result = movieRepo.fullGraph(limit);
//		return toD3Format(result);
//	}