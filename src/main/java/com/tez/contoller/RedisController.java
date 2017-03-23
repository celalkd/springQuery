/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.contoller;

import com.tez.config.SpringRedisConfig;
import com.tez.contoller.RedisController.Word;
import com.tez.domain.Movie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
        
        Movie movie = mongoController.findByTitle(title);
        Integer movieID = new Integer(movie.id);
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
    public List<Movie> findByKeywords(@RequestParam(value="keywords", required=true) List<String> keywordList){
        
        ArrayList<Movie> movies = new ArrayList<>();
        
        Set<String> keySet = redisTemplate.keys("*");
        ArrayList<String> keyList = new ArrayList<>();
        keyList.addAll(keySet);
        
        for(String keyword : keywordList){//her bir arama yapılan anahtar kelime için
            
            for(String key : keyList){//key listesinde keylere bakılır
                
                if(key!=null){//key null ise bu key elenmiş demektir
                    List<Word> wordFreqList = this.freqList_by_id(key);//null olmayan keylerin value'ları alınır
                    
                    if(!this.contains(wordFreqList, keyword)){//aranan anahtar kelime bu value'larda yoksa
                        int index = keyList.indexOf(key);
                        keyList.set(index, null);//key null yapılır
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
            Movie m = new MongoController().findById(idInt);
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
        System.out.println(l.toString());
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
