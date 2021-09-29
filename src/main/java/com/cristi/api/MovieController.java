package com.cristi.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cristi.core.Movie;
import com.cristi.core.MovieRepository;


@RestController
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieRepository repo;
    

    @Autowired
    public MovieController(MovieRepository repo) {
        this.repo = repo;
    }
    
    
    @GetMapping(value="/movies",params = {"title"})
    public List<Movie>  searchMatchingMovies(@RequestParam String title){
    	return repo.findByTitle(title);
    }
    
    

    @GetMapping("/movies/{id}") 
    public String getMovieById(@PathVariable String id){
    	//ca are primary
        //practic tot pe asta o apelam numai ca acum ia repoul de postgres
        return repo.getReco(id);
    }
    
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler
    public void internalServerError(IllegalStateException e) {
        logger.error("An error occured!",e);
    }

    
    
    
}
