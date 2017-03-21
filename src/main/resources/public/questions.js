function search() {
    
    //parse question sentence
    var input = $("#question").val();
    var list = input.split(/[\s|?|,]+/);  
    var titleForUrl = input.match(/"([^"]+)"/)[1];
    
    //set which information is asked
    var isDirector=false;
    var isRating=false;
    var isYear=false;
    var isGenre=false;
    var isStarring=false;
    var isFreqTerms=false;    
    
    for (var i = 0; i < list.length; i++) {
        if(list[i]==='director'){
            isDirector = true;
        }
        if(list[i]==='rating'){
            isRating = true;
        }
        if(list[i]==='year' || list[i]==='published'){
            isYear = true;
        }
        if(list[i]==='genre'){
            isGenre = true;
        }
        if(list[i]==='cast'){
            isStarring = true;
        }
        if(( list[i]==='frequent' && list[i+1]==='terms') || list[i]==='tags' ){
            isFreqTerms = true;
        }
    }
    //if any mongoDB stored information is asked for
    if(isDirector===true||isRating===true||isGenre===true||isYear===true||isStarring===true){
       
        var url_mongo = "http://localhost:8080/find/byTitle?title=" + titleForUrl;     
        $.ajax({type: "GET",
                url: url_mongo,
                success:function(data){
                    var titleFromData = data.title;
                    if(isDirector)
                        $('.movie-director').text(titleFromData+' is directed by '+data.director.toString());      
                    if(isRating)
                        $('.movie-rating').text('Rating of the '+titleFromData+' is '+data.rating+'/10');
                    if(isYear)
                        $('.movie-year').text(titleFromData+' published in '+data.year);
                    if(isGenre)
                        $('.movie-genre').text('Genre: '+data.genre);
                    if(isStarring)
                        $('.movie-starring').text('Cast Members: '+data.starring);
                    }
                });
    }
    //if redis stored information is asked for
    if(isFreqTerms){
        var url_redis = "http://localhost:8080/redis/freqterms?title=" + titleForUrl;;
        $.ajax({type: "GET",
                url: url_redis ,
                success:function(data){ 
                        var i=0;                        
                        while(data[i]) {
                            $('.movie-freq-terms').append('['+data[i].str+' #'+data[i].freq+'] ');
                            i++;
                        }
                    }
                });
    }      
            
};