function search() {
    
    var input = $("#question").val();
    var list = input.split(/[\s|?|,]+/);    
    
    
    var isDirector=false;
    var isRating=false;
    var isYear=false;
    var isGenre=false;
    var isStarring=false;
    
    var titleForUrl = input.match(/"([^"]+)"/)[1];
    
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
    }
    if(isDirector===true||isRating===true||isGenre===true||isYear===true||isStarring===true){
       
        var url_input = "http://localhost:8080/find/byTitle?title=" + titleForUrl;
       
    
        $.ajax({type: "GET",
                url: url_input ,
                success:function(data){
                    var titleFromData = data.title;
                    if(isDirector)
                        $('.movie-director').text(titleFromData+' is directed by '+data.director.toString());      
                    if(isRating)
                        $('.movie-rating').text('Rating of the '+titleFromData+' is '+data.rating+'/10');
                    if(isYear)
                        $('.movie-year').text(titleFromData+'\'s publish year is '+data.year);
                    if(isGenre)
                        $('.movie-genre').text('Genre: '+data.genre);
                    if(isStarring)
                        $('.movie-starring').text('Cast: '+data.starring);
                    }
                });
    }
        
    
        
};