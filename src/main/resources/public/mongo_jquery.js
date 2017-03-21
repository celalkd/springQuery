function search() {
    
    var input = $("#title").val();
    var url_input = "http://localhost:8080/find/byTitle?title=" + input;
    
    $.ajax({type: "GET",
            url: url_input ,
            success:function(data){
                        $('.movie-director').text('Director: '+data.director.toString());                        
                        $('.movie-rating').text('Rating: '+data.rating);
                        $('.movie-year').text('Publish Year: '+data.year);
                        $('.movie-genre').text('Genre: '+data.genre);
                        $('.movie-starring').text('Starring: '+data.starring);
                    }
                });    
};