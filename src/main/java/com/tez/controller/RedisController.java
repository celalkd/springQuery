/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.controller;

import com.tez.config.SpringRedisConfig;
import com.tez.controller.RedisController.Word;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.tez.domain.MovieMongoDB;
import org.apache.commons.lang.WordUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author celalkd
 */

@RestController
public class RedisController {
    
    ApplicationContext ctx ;
    RedisTemplate< String, String > redisTemplate;
    
    public RedisController(){
        ctx =new AnnotationConfigApplicationContext(SpringRedisConfig.class);
        redisTemplate = (RedisTemplate< String, String >) ctx.getBean("redisTemplate");
        
    }
    
    @RequestMapping("find/tags")
    public List<Word> freqList(@RequestParam(value="title", defaultValue = "Pulp Fiction", required = true) String title){
        MongoController mongoController = new MongoController();
        
        MovieMongoDB movie = mongoController.findByTitle(title);
        Integer movieID = movie.id;
        // Integer movieID = new Integer(movie.id);
        String key = movieID.toString();
        
        List<Word> wordFreqList = new ArrayList<>();
        
        int rowSize = getLastIndex(redisTemplate, key);
        
        for(int row=0; row<rowSize; row++){
            String data = redisTemplate.opsForList().index(key, row);
            Word word = splitData(data);
            wordFreqList.add(word);
        }
        
        return wordFreqList;
    }
    
    @RequestMapping("find/byKeywords")
    public List<MovieMongoDB> findByKeywords(@RequestParam(value="keywords", required=true) List<String> keywordList){
        
        ArrayList<MovieMongoDB> movies = new ArrayList<>();
        
        Set<String> keySet = redisTemplate.keys("*");
        ArrayList<String> keyList = new ArrayList<>();
        keyList.addAll(keySet);
        
        List<String> stopWords= new ArrayList<>();
        stopWords.add("a");
        stopWords.add("an");
        stopWords.add("the");
        stopWords.add("movie");
        stopWords.add("which");
        stopWords.add("where");
        stopWords.add("when");
        stopWords.add("played");
        stopWords.add("plays");
        stopWords.add("as");
        stopWords.add("with");
        stopWords.add("is");
        stopWords.add("are");
        stopWords.add("he");
        stopWords.add("she");
        stopWords.add("was");
        stopWords.add("were");
        stopWords.add("and");
        stopWords.add("or");
        stopWords.add("or");
        stopWords.add("with");
        stopWords.add("character");
        stopWords.add("about");
        stopWords.add("that");
        stopWords.add("of");
        stopWords.add("by");
        
        for(String keyword : keywordList){//her bir arama yapılan anahtar kelime için
            
            if(!stopWords.contains(keyword)){
                keyword = WordUtils.uncapitalize(keyword);
                for(String key : keyList){//key listesinde keylere bakılır
                
                if(key!=null){//key null değil ise
                    List<Word> wordFreqList = this.freqList_by_id(key);//value'lara bakılır
                    
                    if(!this.contains(wordFreqList, keyword)){//aranan anahtar kelime bu value'larda yoksa
                        int index = keyList.indexOf(key);
                        keyList.set(index, null);//key null yapılır
                    }
                }
            }
            }
        }
        
        Iterator<String> iterator = keyList.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if(key==null)
                iterator.remove();
        }
        
        for(String idStr : keyList){
            Integer idInt = Integer.parseInt(idStr);
            MovieMongoDB m = new MongoController().findById(idInt);
            movies.add(m);
        }
        
        return movies;        
    }
    
    public List<Word> freqList_by_id(String key){
        
        List<Word> wordFreqList = new ArrayList<>();
        
        int rowSize = getLastIndex(redisTemplate, key);
        
        for(int row=0; row<rowSize; row++){
            String data = redisTemplate.opsForList().index(key, row);
            Word word = splitData(data);
            wordFreqList.add(word);
        }
        
        return wordFreqList;
    }
    
    public boolean contains(List<Word> wordFreqList, String input){
        for(Word current : wordFreqList){
            if(input.equals(current.str))
                return true;
        }
        return false;
    }
    
    public int getLastIndex(RedisTemplate <String, String> redisTemplate, String key){
        
        Long l = redisTemplate.opsForList().size(key);
        Integer i = l.intValue();
        return i;
    }
    
    public Word splitData(String data){
        String[] strArray = data.split(" ");
        return new Word(strArray[0], Integer.parseInt(strArray[1]));
        
    }
    
    public class Word{
        public String str;
        public int freq;

        @Override
        public String toString() {
            return "Word{" + "str=" + str + ", freq=" + freq + '}';
        }

        public Word(String str, int freq) {
            this.str = str;
            this.freq = freq;
        }
        public Word(){
            
        }
    }
}
