package com.cristi.core;

import java.util.List;

//import org.springframework.data.repository.CrudRepository;
//n am la dependecy jpa-ul

public interface MovieRepository {
	
    Movie getMovie(String id);

    String getReco(String id);
    
    List<Movie> findByTitle(String title);
   
}
