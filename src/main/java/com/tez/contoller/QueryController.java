/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tez.contoller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author celalkd
 */

@RestController
public class QueryController {
    
        List<String> wordlist;
        List<String> mongoCriterias = new ArrayList<>();
        boolean isRedis ;
        boolean isNeo4j ; 
        
        @RequestMapping("/search")
        public String search(@RequestParam(value="question", defaultValue="Who is \"director\", when is the \"publish year\" and what are the \"resemble movies\" and of the movie \"Pulp Fiction\"? ") String question){
            String ret=" ";
            wordlist =new QueryParser().parse(question);

            //mongo kriterleri varsa o kriterleri ekliyoruz
            for(String criteria : wordlist){

                if(criteria.equals("director")||
                    criteria.equals("publish year")||
                    criteria.equals("rating")||
                    criteria.equals("genre")||
                    criteria.equals("cast")||
                    criteria.equals("context"))
                {
                    mongoCriterias.add(criteria);
                    ret=ret+(criteria+"(mongo)");
                }
            }

            //redis veya neo4j sorgu kriteri var mı bakıyoruz
            isRedis = wordlist.contains("frequent terms");
            isNeo4j = wordlist.contains("resemble movies"); 

            //söz konusu filmin başlığını öğreniyoruz
            String title = wordlist.get(wordlist.size()-1);
            
            return ret+(isRedis+"(redis)\n"+isNeo4j+"(neo4j)\n"+title+"(title)");
            
        }
        
        public class QueryParser {

            public List parse(String query){

                Pattern p = Pattern.compile("\"([^\"]*)\"");//"quote"; iki tırnak arası
                Matcher m = p.matcher(query);

                List<String> list = new ArrayList();

                while (m.find()) {
                  System.out.println(m.group(1));
                  list.add(m.group(1));
                }
                return list;
            }

        }    
}
