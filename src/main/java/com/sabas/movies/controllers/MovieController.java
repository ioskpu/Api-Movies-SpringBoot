package com.sabas.movies.controllers;

import com.sabas.movies.models.Movie;
import com.sabas.movies.repositoties.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;


    @CrossOrigin
    @GetMapping
    public List<Movie> getAllMovie(){
        return movieRepository.findAll();
    }
}
