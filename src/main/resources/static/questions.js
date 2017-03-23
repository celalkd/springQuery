    function searchTags() {
        $("table#results tbody").empty();
        $("table#results thead").empty();
        $("table#tags tbody").empty();
        $("table#tags thead").empty();
        $("#poster").empty();
        $("table#plot tbody").empty();
        
        var input = $("#question").val();
        var list = input.split(/[\s|?|,|.]+/);  
        var titleForUrl = input.match(/"([^"]+)"/)[1];

        var isDirector=false;
        var isRating=false;
        var isYear=false;
        var isGenre=false;
        var isStarring=false;
        var isFreqTerms=false;    
        var isAll=false;
        var isPlot=false;

        for (var i = 0; i < list.length; i++) {
            if(list[i]==='all'){
                isAll = true;
                break;
            }
            if(list[i]==='director')
                isDirector = true;
            if(list[i]==='rating')
                isRating = true;
            if(list[i]==='year' || list[i]==='published')
                isYear = true;
            if(list[i]==='genre')
                isGenre = true;
            if(list[i]==='cast')
                isStarring = true;
            if(( list[i]==='frequent' && list[i+1]==='terms') || list[i]==='tags' )
                isFreqTerms = true;
            if(list[i]==='plot')
                isPlot= true;
        }
        
        
        var url_mongo = "http://localhost:8080/find/byTitle?title=" + titleForUrl;     
            $.ajax({type: "GET",
                    url: url_mongo,
                    success:function(data){

                        append_tr_element();
                        debugger;
                        appendTitleandPoster(data);
                        debugger;
                        if(!isAll){
                            if(isDirector)
                                appendDirector(data);
                            if(isYear)
                                appendYear(data);
                            if(isGenre)
                                appendGenre(data);
                            if(isStarring)
                                appendStarring(data);
                            if(isRating)
                                appendRating(data);
                            if(isFreqTerms)
                                appendFrequentTerms(data);
                            if(isPlot)
                                appendPlot(data);
                            
                        }
                        else if(isAll){
                                appendDirector(data);
                                appendYear(data);
                                appendGenre(data);
                                appendStarring(data);
                                appendRating(data);
                                appendFrequentTerms(data);
                                appendPlot(data);
                        }
                    }
                });

    }
    
    function append_tr_element(){
        var t_body = $("table#results tbody").empty();
        var t_head = $("table#results thead").empty();
        $('<tr> </tr>').appendTo(t_body);
    }
    function appendTitleandPoster(movie){
        var t = $("table#results thead");
        $("<th>Title</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td class='movie'>"+movie.title+"</td>").appendTo(tr);
        
        var poster_src = movie.poster;
        $("#poster").attr("src",poster_src);
    }
    function appendDirector(movie){
        var t = $("table#results thead");
        $("<th>Director</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td class='director'>"+movie.director+"</td>").appendTo(tr);
    }
    function appendYear(movie){
        var t = $("table#results thead");
        $("<th>Year</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td>"+movie.year+"</td>").appendTo(tr);
    }
    function appendRating(movie){
        var t = $("table#results thead");
        $("<th>Rating</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td>"+movie.rating+"</td>").appendTo(tr);
    }
    function appendGenre(movie){
        var t = $("table#results thead");
        $("<th>Genre</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td id='tdGenre'</td>").appendTo(tr);

        var genreList = movie.genre;
        var tdGenre = $("#tdGenre").empty();

        genreList.forEach(function (genre) {$("<li>" + genre + "</li>").appendTo(tdGenre);});
    }
    function appendStarring(movie){
        var t = $("table#results thead");
        $("<th>Starring</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td id='tdStarring'</td>").appendTo(tr);

        var starringList = movie.starring;
        var tdStarring = $("#tdStarring").empty();

        starringList.forEach(function (genre) {$("<li>" + genre + "</li>").appendTo(tdStarring);});
    }
    function appendFrequentTerms(data){
            debugger;
            var url_redis = "http://localhost:8080/find/tags?title=" + data.title;
            debugger;
            $.ajax({type: "GET",
                    url: url_redis ,
                    success:function(tags){                             
                            var t_body = $("table#tags tbody").empty();
                            var t_head = $("table#tags thead").empty();
                            $('<tr> </tr>').appendTo(t_body);
                            
                            $("<th>Tag</th>").appendTo(t_head);
                            $("<th>Frequency</th>").appendTo(t_head);
                                
                            var tr = $("table#tags tbody tr");
                            $("<td id='td_str'</td>").appendTo(tr);
                            $("<td id='td_freq'</td>").appendTo(tr);
                            
                            var td_str = $("#td_str").empty();
                            var td_freq = $("#td_freq").empty();
                            
                            var i =0;
                            while(tags[i] && i<20){
                                $("<li>[" + tags[i].str + "]</li>").appendTo(td_str);
                                $("<li>#" + tags[i].freq + "</li>").appendTo(td_freq);
                                i++;
                            }
                            
                        }
                    });
    }
    function appendPlot(movie){
        
        var t = $("table#plot tbody").empty();
        $("<tr><td>"+movie.plot+"</td></tr>").appendTo(t);
        debugger;
        
    }
    function searchRedis(){
        var words;
        var sentence;

        var input = $("#question").val();
        var list = input.split(/[\s,]+/); 
        var urlAppend="";
        for(var i=0; i<list.lenght;i++){

        }

         var url_firstRedisFunc="http://localhost:8080/find/byKeywords?keywords="+list;

                              $.ajax({type:"GET",
                              url: url_firstRedisFunc,
                          success:function(data){
                             debugger;
                              var x=0;
                              while(data[x]){
                                  $('.redis-Freqword').append(data[x].title+" - ");
                                  x++;
                              }


                          }
                      });
    }