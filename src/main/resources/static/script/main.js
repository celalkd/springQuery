    var lang_tr = {
            //search for tags
            dir : ['yönetmeni','yönetmiş'],
            rate : ["puanı","puan"],
            all: [ "tümü","tüm","tümünü","tamamını","hepsini"],
            year : ["yılı","yayınlanmış","yapılmış","çekilmiş"],
            genre : "türü",
            plot : ["konusu","hakkında","anlatıyor"],
            reco : ["benzer","önerileri","önerilerin","önerirsin","öneri"],
            cast : ["oyuncuları","oynamış"],
            tags : ["sık","çok","anahtar"],
            
            //GUI titles
            Title: "Başlık",
            Director: "Yönetmen",
            Starring:"Oyuncular",
            Genre:"Tür",
            Rating:"Puan",
            Year:"Yıl",
            Details:" Detaylar",
            Results: " Sorgu Sonuçları",
            Plot:" Konu",
            FreqTerms:"   Sık Geçen Kelimeler",
            FreqTermsTag:"Kelime",
            FreqTermsFreq:"Frekans",
            RecoGraph:"   Öneri Çizgesi",
            Reco:"   Önerilen Filmler",
            RecoSource:"Kaynak Film",
            RecoResemble:"Öneriler",
            Submit:" Ara ",
            
            //search for movies
            directedby: [" yönetmeni ","Yönetmeni "],
            notdirectedby: ["not yönetmeni ","notYönetmeni ","not yönetmeni  ","notYönetmeni  "],
            ingenreof: [" türü ","Türü "],
            notingenreof: ["not türü ", "notTürü ","not türü  ", "notTürü  "],
            starring: [" oyuncuları arasında ","Oyuncuları arasında "],
            notstarring: ["not oyuncuları arasında ","notOyuncuları Arasında ","not oyuncuları arasında  ","notOyuncuları arasında  "],
            ratedminimum:[" puanı en az ","Puanı en az "," minimum puanı ","Minimum paunı "],
            publishedin: [" yapım yılı ", "Yapım yılı "],
            publishedbetween: [" yapım yılı aralığı ", "Yapım yılı aralığı "],
            publishedbefore: [" en yeni yapım yılı ","En yeni yapım yılı "],
            publishedafter: [" en eski yapım yılı ","En eski yapım yılı "],
            
            delimiterForMovieSearch: [" yönetmeni ","Yönetmeni "," türü ","Türü ",
            "not türü "," oyuncuları arasında ","Oyuncuları arasında ", "not oyuncuları arasında ",
            " puanı en az ","Puanı en az "," minimum puanı ","Minimum paunı ",
            " yapım yılı ", "Yapım yılı "," yapım yılı aralığı ", "Yapım yılı aralığı ",
            " en yeni yapım yılı ","En yeni yapım yılı "," en eski yapım yılı ","En eski yapım yılı "]
            
            
            
        };
    var lang_en = {
        
            //search for tags
            dir : ['director',"directed"],
            rate : ["rating"],
            year : "published",
            all: ["all"],
            genre : "genre",
            plot : ["plot","about"],
            reco : ["recommendations","similar"],
            cast : ["cast","stars","actors"],
            tags: ["tags","words"],
            
            //GIU Titles
            Title: "Title",
            Director: "Director",
            Starring:"Starring",
            Genre:"Genre",
            Rating:"Rating",
            Year:"Year",
            Details:" Details",
            Plot:" Plot",            
            Results: " Search Results",
            FreqTerms:"   Frequent Terms",
            FreqTermsTag:"Tags",
            FreqTermsFreq:"Frequency",
            RecoGraph:"   Recommendation Graph",
            Reco:"   Recommended Movies",
            RecoSource:"Source",
            RecoResemble:"Resemble Movies",
            Submit:"Submit",
           
            //Search for movies
            directedby: [" directed by "],
            notdirectedby: ["not directed by "],
            ingenreof: [" in genre of "],
            notingenreof: [" not in genre of "],
            starring: [" starring "],
            notstarring: [" not starring "],
            ratedminimum: [" rated minimum "],
            publishedin: [" published in "],
            publishedbetween: [" published between "],
            publishedbefore: [" published before "],
            publishedafter: [" published after "],
            delimiterForMovieSearch: ["Find"]
    };
    var current_lang = lang_en;
    
    var isDirector = false;
    var isRating = false;
    var isYear = false;
    var isGenre = false;
    var isStarring = false;
    var isFreqTerms = false;
    var isAll = false;
    var isPlot = false;
    var isRedis = false;
    var isRecommendation = false;
    var isTags = false;
    var isTR = false;
    var success=false;
        
    function refreshData(){
        isDirector=false;
        isRating=false;
        isYear=false;
        isGenre=false;
        isStarring=false;
        isFreqTerms=false;    
        isAll=false;
        isPlot=false;
        isRedis = false;
        isRecommendation = false;
        isTags = false;
        cleanHtmlElements();
    }
    function cleanHtmlElements(){
        
        $("table#results tbody").remove();
        $("table#results thead").empty();
        $("table#tags tbody").empty();
        $("table#tags thead").empty();
        $("#poster").empty();
        $("table#plot tbody").empty();
        $("#doughnutChart").empty();
        $("#graphDiv").empty();
        $("#resembleMovies").empty();
    }
    function toggleLanguage(){
        $(".toggle-button").toggleClass('toggle-button-selected'); 
        isTR = !isTR ;
        if(isTR){
            alert("Secilen Dil: Turkce");
            current_lang = lang_tr;
            
        }
        else {
            alert("Selected Language: English");
            current_lang = lang_en;
        }
        $("#submitButton").text(current_lang.Submit);
    }    
    
    function anyQuery(){
        
        
        refreshData();
        var input = $("#question").val();
        
        if(input[0]==="\'"){
            searchRedisKeywords();   
            isRedis = true;
            $('#plot_res').removeClass('col-md-6').addClass('col-md-12');
            $('#redisSVG').show();
        }
        
        else {
            
            var done;
            
            if(current_lang===lang_tr){
                var delimiter = input.split(/[\"]+/)[0];
                for (var i = 0; i <= current_lang.delimiterForMovieSearch.length; i++) {

                    if (delimiter === current_lang.delimiterForMovieSearch[i]) {
                        searchMovies(delimiter);
                        isTags = true;
                        $('#plot_res').removeClass('col-md-6').addClass('col-md-12');
                        $('#redisSVG').hide();
                        done = true;       
                        break;
                    }
                }
            }
            else{
                var delimiter = input.split(/[\s]+/)[0];
                if (delimiter === current_lang.delimiterForMovieSearch[0]) {
                    debugger;
                    searchMovies("Find movies");
                    isTags = true;
                    $('#plot_res').removeClass('col-md-6').addClass('col-md-12');
                    $('#redisSVG').hide();
                    done = true;
                }
            }
            
            if(!done) {
                searchTags();
                $('#plot_res').removeClass('col-md-12').addClass('col-md-6');
                $('#redisSVG').hide();
            }
        }
    }
    
    function searchRedisKeywords(){
        $("#searchResults").text(current_lang.Results);
        $("#mongoTagsPanel").css("height", "");
        
        var input = $("#question").val();
         
        var description = input.match(/'([^"]+)'/)[1];
        var list = description.split(/[\s]+/); 
        var url_firstRedisFunc="http://localhost:8080/find/byKeywords?keywords="+list;
        
        append_all_th();
        $.ajax({
                type: "GET",
                url: url_firstRedisFunc,
                success: function (data) {
                    if(data.length>0){
                        success=true;
                        var x = 0;
                        while (data[x]) {
                            append_all_body(data[x],x);
                            x++;
                        }
                    }
                    else{
                        success=false;
                        alert("Girdiğiniz anahtar kelimleri içeren bir film bulunmamaktadır.");
                    }
                }
            });
    }
    function searchTags() {
        
        $("#searchResults").text(current_lang.Results);
        $("#detailsPanel").text(current_lang.Details);
        $("#plotPanel").text(current_lang.Plot);
        $("#RecommendedMovies").text(current_lang.Reco);
        $("#RecommendationGraph").text(current_lang.RecoGraph);
        $("#TagsPanel").text(current_lang.FreqTerms);
        $("#recMovSourceTh").text(current_lang.RecoSource);
        $("#recMovResTh").text(current_lang.RecoResemble);            
            
        $("#mongoTagsPanel").css("height", "471px");
        
        var input = $("#question").val();
        var list = input.split(/[\s|?|,|.]+/);  
        var titleForUrl = input.match(/"([^"]+)"/)[1];


        for (var i = 0; i < list.length; i++) {
            for(var j = 0; j<=4; j++){
                if (list[i] === current_lang.all[j]) {
                    isAll = true;
                    break;
                }
            }
            if(isAll)
                break;
            for(var j = 0; j<current_lang.dir.length; j++){
                if(list[i]===current_lang.dir[j]){
                    isDirector = true;
                    break;
                }
            }
            for (var j = 0; j < current_lang.rate.length; j++) {
                if (list[i] === current_lang.rate[j]) {
                    debugger;
                    isRating = true;
                    break;
                }
            }
            for (var j = 0; j < current_lang.year.length; j++) {
                if (list[i] === current_lang.year[j]) {
                    isYear = true;
                    break;
                }
            }
            
            
            if(list[i]===current_lang.genre)
                isGenre = true;
            for (var j = 0; j < current_lang.cast.length; j++) {
                if (list[i] === current_lang.cast[j]) {
                    isStarring = true;
                    break;
                }
            }
            for(var j = 0; j<current_lang.tags.length; j++){
                if(list[i]===current_lang.tags[j]){
                    isFreqTerms = true;
                    break;
                }
            }
            for(var j = 0; j<current_lang.plot.length; j++){
                if(list[i]===current_lang.plot[j]){
                    isPlot = true;
                    break;
                }
            }
            for(var j = 0; j<current_lang.reco.length; j++){
                if(list[i]===current_lang.reco[j]){
                    isRecommendation = true;
                    break;
                }
            }
        }
        debugger;
        var url_mongo = "http://localhost:8080/find/byTitle?title=" + titleForUrl; 
 
            $.ajax({type: "GET",
                    url: url_mongo,
                    asycn: false,
                    success:function(data){
                        if(data!==""){
                            debugger;
                            success=true;
                            append_tr_element();
                            appendTitleandPoster(data);
                            if(!isAll){
                                if(isDirector)
                                   appendDirector(data);
                                if(isYear)
                                    appendYear(data);
                                if(isGenre)
                                    appendGenre(data);
                                if(isStarring)
                                    appendStarring(data);
                                if(isRating){
                                    appendRating(data);

                                }
                                if(isFreqTerms){ 
                                    appendFrequentTerms(data);
                                }
                                if(isPlot){ 
                                    appendPlot(data);
                                }
                                if(isRecommendation)
                                    appendForcedGraph(data);
                            }
                            else if(isAll){
                                    appendDirector(data);
                                    appendYear(data);
                                    appendGenre(data);
                                    appendStarring(data);
                                    appendRating(data);
                                    appendFrequentTerms(data);
                                    appendPlot(data);
                                    appendForcedGraph(data);
                            }
                        }
                        else{
                            debugger;
                            alert("Bilgi almak istediğiniz film veri tabanımıza eklenmek üzeredir. Sabrınız için teşekkür ederiz.");
                            success=false;
                            debugger;
                        }
                    },
                    error: function(){
                        debugger;
                        alert('Hatalı Giriş');
                    }

                });
    }
    function searchMovies(delimiter){
        
        var ama;
        
        $("#mongoTagsPanel").css("height", "");var question="";        
        var userInput = $("#question").val();        
        if(current_lang===lang_en){
            //Find movies kısmı çıkarılmalı
            var fullQuery = userInput.split(delimiter)[1];
            var query = fullQuery.split(/[,]+/);
        }
        else if(current_lang===lang_tr){
            var negative_expressions = [];
            var query = userInput.split(/[,]+/);
            for(var index in query){
                var ama = query[index].split(" olan")[1];
                debugger;
                if(ama != null){
                    debugger;
                    ama = ama.split(" ")[1];
                    
                }
                debugger;
                if(ama==="ama"){
                    var positive = query[index].split(" olan")[0];
                    var current_selector = positive.split("\"")[0];
                    var negative = current_selector + query[index].split(" ama")[1];
                    negative_expressions.push(negative);
                    query[index] = positive;
                    debugger;
                    
                }
                    
                //query[index] = query[index].split(" olan")[0];
                
                
            }
            if(negative_expressions.length>0){
                debugger;
                for(var e in negative_expressions){
                    query.push(negative_expressions[e]);
                }
            }
        }
        debugger;
        
        for(var colon in query){
           
            
            var inQuotes = query[colon].split('\"');
            var selector = inQuotes[0];
            var inQuotes_2 = inQuotes[2].split(" ")[1];
            
            if(isTR && (inQuotes_2==="olmayan" || inQuotes_2==="olmayanlar")){
                debugger;
                selector = "not".concat(selector);
                debugger;
            }
            debugger;
            selector = getSelector(selector);
            debugger;
            
            switch(selector){
                
                case current_lang.directedby[0]:
                    var directorValue = inQuotes[1];
                    question = question+"director="+directorValue+"&";
                    break;
                case current_lang.notdirectedby[0]:
                    var notdirectorValue = inQuotes[1];
                    question = question+"notdirector="+notdirectorValue+"&";
                    break;
                case current_lang.ratedminimum[0]:
                    var ratingValue = inQuotes[1];
                    question = question+"rating="+ratingValue+"&";
                    break;
                case current_lang.starring[0]:
                    var stars = inQuotes[1].split("/");
                    question = question + "starring=";

                    var lastIndex = stars.length - 1;
                    var i = 0;
                    while (i <= lastIndex) {
                        if (i === lastIndex) {
                            question = question + stars[i] + "&";
                        } else
                            question = question + stars[i] + ",";
                        i++;
                    }                          
                    
                    break;
                case current_lang.notstarring[0]:
                    debugger;
                    var stars = inQuotes[1].split("/");
                    question = question + "notstarring=";

                    var lastIndex = stars.length - 1;
                    var i = 0;
                    while (i <= lastIndex) {
                        if (i === lastIndex) {
                            question = question + stars[i] + "&";
                        } else
                            question = question + stars[i] + ",";
                        i++;
                    }
                    break;
                
                case current_lang.ingenreof[0]:
                    var genres = inQuotes[1].split("/");
                    question = question+"genre=";
                    
                    var lastIndex = genres.length-1;
                    var i=0;
                    while(i<=lastIndex){
                        if(i===lastIndex){
                            if(isTR){
                                var gen = translateGenre(genres[i]);
                                question = question+gen+"&";
                            }
                            else question = question+genres[i]+"&";
                        }
                        else{
                            if(isTR){
                                var gen = translateGenre(genres[i]);
                                question = question+gen+",";
                            }
                            else 
                                question = question+genres[i]+",";
                        }
                        i++;
                    }
                    break;
                case current_lang.notingenreof[0]:
                    var genres = inQuotes[1].split("/");
                    question = question + "notgenre=";

                    var lastIndex = genres.length - 1;
                    var i = 0;
                    while (i <= lastIndex) {
                        if (i === lastIndex) {
                            if(isTR){
                                var gen = translateGenre(genres[i]);
                                question = question+gen+"&";
                            }
                            else question = question+genres[i]+"&";
                        } else{
                            if(isTR){
                                var gen = translateGenre(genres[i]);
                                question = question+gen+",";
                            }
                            else 
                                question = question+genres[i]+",";
                        }
                        i++;
                    }
                    debugger;
                    break;
                case current_lang.publishedin[0]:
                    question = question+"yearMin="+inQuotes[1]+"&"+"yearMax="+inQuotes[1]+"&";
                    break;
                case current_lang.publishedafter[0]:
                    question = question+"yearMin="+inQuotes[1]+"&";
                    break;
                case current_lang.publishedbefore[0]:
                    question = question+"yearMax="+inQuotes[1]+"&";
                    break;    
                case current_lang.publishedbetween[0]:
                    var list3 = inQuotes[1].split("-");
                    question = question+"yearMin="+list3[0]+"&";
                    question = question+"yearMax="+list3[1]+"&";
                    break;   
                
            }
            
        }
        var url_mongo = "http://localhost:8080/find/byTags?" + question;   
        
        append_all_th();
        $.ajax({type: "GET",
                    url: url_mongo,
                    asycn: false,
                    success:function(data){
                        debugger;
                        if(data.length>0){
                            success=true;
                            var x = 0;
                            while (data[x]) {
                                append_all_body(data[x],x);
                                x++;
                            }
                        }
                        else{
                            success=false;
                            alert("Veritabanımızda aradığınız kriterlere uygun film bulunamamıştır.");
                        }
                    }
                });
           
        
    }
    
    function append_all_th(){
        var t = $("table#results thead");
        $("<th>"+current_lang.Title+"</th>").appendTo(t);
        $("<th>"+current_lang.Director+"</th>").appendTo(t);
        $("<th>"+current_lang.Year+"</th>").appendTo(t);
        $("<th>"+current_lang.Genre+"</th>").appendTo(t);
        $("<th>"+current_lang.Starring+"</th>").appendTo(t);
        $("<th>"+current_lang.Rating+"</th>").appendTo(t);
    }
    function append_all_body(movie, index){
        
        
        var element_id = "body"+index;
        var table = $("table#results");
        $('<tbody id="'+element_id+'"> </tbody>').appendTo(table);
        
        var t_body = $("#"+element_id);
        
        element_id="tr"+index;
        $('<tr id="'+element_id+'"> </tr>').appendTo(t_body);
       
        
        var tr = $("#"+element_id);
        
        if(!isTR)
            $("<td class='movie' id='title_"+index+"'>"+movie.title+"</td>").appendTo(tr);
        else
            $("<td class='movie' id='title_"+index+"'>"+movie.başlık+"</td>").appendTo(tr);
        $("#title_"+index).css("font-weight", "bold");
        
        $("<td class='director'>"+movie.director+"</td>").appendTo(tr);
        $("<td class='year'>"+movie.year+"</td>").appendTo(tr);
        
        
        element_id="tdGenre"+index;
        $("<td id='"+element_id+"'</td>").appendTo(tr);
            var genreList = movie.genre;
            var tdGenre = $("#"+element_id);
            if(isTR){
                genreList.forEach(function (genre){
                    var genreTr = translateGenre(genre);
                    $("<li>" + genreTr + "</li>").appendTo(tdGenre);
                });
            }
            else
                genreList.forEach(function (genre){$("<li>" + genre + "</li>").appendTo(tdGenre);});
    
            
        //genreList.forEach(function (genre) {$("<li>" + genre + "</li>").appendTo(tdGenre);});
            
            
        element_id="tdStar"+index;
        $("<td id='"+element_id+"'</td>").appendTo(tr);
            var starList = movie.starring;
            var tdStar = $("#"+element_id);
            starList.forEach(function (star) {$("<li>" + star + "</li>").appendTo(tdStar);});
            
        $("<td id='rating'>"+movie.rating+"</td>").appendTo(tr);
    }
    function append_tr_element(){
        var table = $("table#results");
        $('<tbody id="resBody"> </tbody>').appendTo(table);
        var t_body = $("table#results tbody#resBody");
        $('<tr> </tr>').appendTo(t_body);
    }
    function appendTitleandPoster(movie){
        
        var t = $("table#results thead");
        
        var current_lang;
        if(isTR)
            current_lang = lang_tr;
        else current_lang = lang_en;
        
        $("<th>"+current_lang.Title+"</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        if(!isTR)
            $("<td class='movie'>"+movie.title+"</td>").appendTo(tr);
        else
            $("<td class='movie'>"+movie.başlık+"</td>").appendTo(tr);

        var poster_src = movie.poster;
        $("#poster").attr("src",poster_src);
    }
    function appendDirector(movie){
        var t = $("table#results thead");
        $("<th>"+current_lang.Director+"</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td class='director'>"+movie.director+"</td>").appendTo(tr);
    }
    function appendYear(movie){
        var t = $("table#results thead");
        $("<th>"+current_lang.Year+"</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td>"+movie.year+"</td>").appendTo(tr);
    }
    function appendRating(movie){
        debugger;
        var t = $("table#results thead");
        $("<th>"+current_lang.Rating+"</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td id='rating'>"+movie.rating+"</td>").appendTo(tr);
    }
    function translateGenre(genre){
        
        var genres = [
            
                {en: "Comedy", tr: "Komedi"}, 
                {en: "Drama", tr: "Dram"}, 
                {en: "Crime", tr: "Suç"},
                {en: "Mystery", tr: "Gizem"},
                {en: "Thriller", tr: "Gerilim"},
                {en: "Romance", tr: "Romantik"},
                {en: "Sci-Fi", tr: "Bilimkurgu"},
                {en: "Fantasy", tr: "Fantastik"},
                {en: "Biography", tr: "Biyografi"},
                {en: "War", tr: "Savaş"},
                {en: "Adventure", tr: "Macera"},
                {en: "Western", tr: "Kovboy"},
                {en: "Action", tr: "Aksiyon"},
                {en: "Thriller", tr: "Gerilim"},
                {en: "History", tr: "Tarih"}
            
        ];
        
        var translation = null;
        genres.forEach(function (line){
            
            if(line.tr ===genre){
                translation = line.en;
            }
            else if(line.en===genre){
                translation = line.tr;
            }
        });
        return translation;
    }
    function appendGenre(movie){
        var t = $("table#results thead");
        $("<th>"+current_lang.Genre+"</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td id='tdGenre'</td>").appendTo(tr);

        var genreList = movie.genre;
        var tdGenre = $("#tdGenre").empty();
        
        if(isTR){
            genreList.forEach(function (genre){
                var genreTR = translateGenre(genre);
                $("<li>" + genreTR + "</li>").appendTo(tdGenre);
            });
        }
        else
            genreList.forEach(function (genre){$("<li>" + genre + "</li>").appendTo(tdGenre);});
    }
    function appendStarring(movie){
        var t = $("table#results thead");
        $("<th>"+current_lang.Starring+"</th>").appendTo(t);

        var tr = $("table#results tbody tr");
        $("<td id='tdStarring'</td>").appendTo(tr);

        var starringList = movie.starring;
        var tdStarring = $("#tdStarring").empty();

        starringList.forEach(function (genre) {$("<li>" + genre + "</li>").appendTo(tdStarring);});
    }
    function appendFrequentTerms(data){
            if(isTR)
                var url_redis = "http://localhost:8080/find/tags/tr?title=" + data.title;
            else
                var url_redis = "http://localhost:8080/find/tags?title=" + data.title;
            $.ajax({type: "GET",
                    url: url_redis ,
                    success:function(tags){                             
                            var t_body = $("table#tags tbody").empty();
                            var t_head = $("table#tags thead").empty();
                            $('<tr> </tr>').appendTo(t_body);
                            
                            $("<th>"+current_lang.FreqTermsTag+"</th>").appendTo(t_head);
                            $("<th>"+current_lang.FreqTermsFreq+"</th>").appendTo(t_head);
                                
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
        if(!isTR)
            $("<tr><td>"+movie.plot+"</td></tr>").appendTo(t);
        else
            $("<tr><td>"+movie.konu+"</td></tr>").appendTo(t);
        
    }
    function appendDonutChart(id){
            
            
            var rating = $("#rating").text();
            var negative; 
            var ratingFloat;
            
            if(rating!==""){
                ratingFloat = parseFloat(rating);
                negative = Math.round((10-ratingFloat)*100)/100;
            }
            
        
            $("#"+id).drawDoughnutChart([
                    { 
                      title: "Broken", 
                      value : negative,  
                      color: "#1A1C1E" 
                      
                    },
                    { 
                      title: "Rating",
                      value:  ratingFloat,   
                      color:  "#58FBD4"
                    }
                  ]);
   
    }

    $(document).on({
        ajaxStart: function() { 
                        $("#mongoTagsPanel").hide(0);
                        $("#mongoPlotPanel").hide(0);
                        $("#mongoDetailsPanel").hide(0);
                        $("#redisPanel").hide(0);
                        $("#neo4jPanel").hide(0);
                        $("#neo4jPanel_titles").hide(0);
        }
        ,
        ajaxStop: function() {
          setTimeout(function() { 
                        if(isAll && success){
                            $("#mongoTagsPanel").fadeIn(1500);
                            $("#mongoDetailsPanel").fadeIn(1500);
                            $("#mongoPlotPanel").fadeIn(1500); 
                            $("#redisPanel").fadeIn(1500);
                            $("#neo4jPanel").fadeIn(1500);
                            $("#neo4jPanel_titles").fadeIn(1500);
                            appendDonutChart("doughnutChart");
                            
                        }
                        else if(success){
                            debugger;
                            $("#mongoTagsPanel").fadeIn(1500);
                            if(isRecommendation){
                                $("#neo4jPanel").fadeIn(1500);
                                 $("#neo4jPanel_titles").fadeIn(1500);
                            }
                            if(!isRedis && !isTags)
                                $("#mongoDetailsPanel").fadeIn(1500);
                            if(isPlot)
                                $("#mongoPlotPanel").fadeIn(1500);                        
                            if(isFreqTerms)
                                $("#redisPanel").fadeIn(1500);
                            appendDonutChart("doughnutChart");
                        }
                        
                      }, 100);
        }    
    });
    $(document).ready(function(){
     $("#langSelector").change(function(){
         var value = document.getElementById("langSelector").value;
         if(value==="Türkçe"){
             isTR = true;
             current_lang = lang_tr;
             //alert("Arayüz Dili Türkçe");
         }
         else if(value==="English"){
             isTR = false;
             current_lang = lang_en;
             
         }
         $("#submitButton").text(current_lang.Submit);
     });
    });
   
    function appendForcedGraph(movie){
        
    var d3 = window.d3 || {};
    
    var width = 1000, height = 500;

    var force = d3.layout.force()
            .charge(-600).linkDistance(30).size([width, height]);
    
    var svg = d3.select("#graphDiv").append("svg")
            .attr("width", "100%").attr("height", "100%")
            .attr("pointer-events", "all");
    
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/recommendation/"+movie.title,
        success: function (graph) {
            debugger;
            force.nodes(graph.nodes).links(graph.links).start();
            
            var td_source = $("#sourceMovie");
            var td_resemble = $("#resembleMovies");
            debugger;
            for(var x in graph.nodes){
                var l = graph.nodes[x].label;
                if(l==="Movie"){
                    debugger;
                    if(graph.nodes[x].title===movie.title){
                        debugger;
                        td_source.text(graph.nodes[x].title);
                    }
                    else {
                        $("<li>" + graph.nodes[x].title + "</li>").appendTo(td_resemble);
                    }
                }
                
            }
            debugger;
            var link = svg.selectAll(".link")
                    .data(graph.links).enter()
                    .append("line").attr("class", "link");
            var node = svg.selectAll(".node")
                    .data(graph.nodes).enter()
                    .append("circle")
                    .attr("class", function (d) {
                        return "node " + d.label;
                    })
                    .attr("r", function (d) {
                        if (d.label === "Movie")
                            return 24;
                        else if (d.label === "Director")
                            return 20;
                        else if (d.label === "Genre")
                            return 16;
                        else if (d.label === "Star")
                            return 12;
                    }
                    )
                    .call(force.drag);
            // html title attribute
            node.append("title")
                    .text(function (d) {
                        var x;
                        if (d.label === "Movie")
                            return d.title;
                        else
                            return d.name;
                    });

            // force feed algo ticks
            force.on("tick", function () {
                link.attr("x1", function (d) {
                    return d.source.x;
                })
                        .attr("y1", function (d) {
                            return d.source.y;
                        })
                        .attr("x2", function (d) {
                            return d.target.x;
                        })
                        .attr("y2", function (d) {
                            return d.target.y;
                        });
                node.attr("cx", function (d) {
                    return d.x;
                })
                        .attr("cy", function (d) {
                            return d.y;
                        });
            });

        }
    });
    debugger;
}

function getSelector(selector){
    for (var i = 0; i < current_lang.directedby.length; i++) {
        
        if (current_lang.directedby[i] === selector)
            return current_lang.directedby[0];
    }
    for (var i = 0; i < current_lang.notdirectedby.length; i++) {
        
        if (current_lang.notdirectedby[i] === selector)
            return current_lang.notdirectedby[0];
    }
    for (var i = 0; i < current_lang.ingenreof.length; i++) {
        if (current_lang.ingenreof[i] === selector)
            return current_lang.ingenreof[0];
    }
    for (var i = 0; i < current_lang.notingenreof.length; i++) {
        if (current_lang.notingenreof[i] === selector)
            return current_lang.notingenreof[0];
    }
    for (var i = 0; i < current_lang.ratedminimum.length; i++) {
        if (current_lang.ratedminimum[i] === selector)
            return current_lang.ratedminimum[0];
    }
    for (var i = 0; i < current_lang.starring.length; i++) {
        if (current_lang.starring[i] === selector)
            return current_lang.starring[0];
    }
    for (var i = 0; i < current_lang.notstarring.length; i++) {
        debugger;
        if (current_lang.notstarring[i] === selector)
            return current_lang.notstarring[0];
        
    }
    for (var i = 0; i < current_lang.publishedin.length; i++) {
        if (current_lang.publishedin[i] === selector)
            return current_lang.publishedin[0];
    }
    for (var i = 0; i < current_lang.publishedin.length; i++) {
        if (current_lang.publishedin[i] === selector)
            return current_lang.publishedin[0];
    }
    for (var i = 0; i < current_lang.publishedafter.length; i++) {
        if (current_lang.publishedafter[i] === selector)
            return current_lang.publishedafter[0];
    }
    for (var i = 0; i < current_lang.publishedbefore.length; i++) {
        if (current_lang.publishedbefore[i] === selector)
            return current_lang.publishedbefore[0];
    }
    for (var i = 0; i < current_lang.publishedbetween.length; i++) {
        if (current_lang.publishedbetween[i] === selector)
            return current_lang.publishedbetween[0];
    }
    return null;
    
}


    
//    function append_tr_element(){
//        var t_body = $("table#results tbody#resBody").empty();
//        $('<tr> </tr>').appendTo(t_body);
//    }
//    function appendTitleandPoster(movie){
//        var t = $("table#results thead");
//        $("<th>Title</th>").appendTo(t);
//
//        var tr = $("table#results tbody tr");
//        $("<td class='movie'>"+movie.title+"</td>").appendTo(tr);
//        
//        var poster_src = movie.poster;
//        $("#poster").attr("src",poster_src);
//    }