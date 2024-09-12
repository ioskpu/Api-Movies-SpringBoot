package com.sabas.movies.controllers;

import com.sabas.movies.models.Movie;
import com.sabas.movies.repositoties.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @CrossOrigin
    @GetMapping
    public List<Movie> getAllMovie(){
        return movieRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getAllMoviesById(@PathVariable Long id){
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        if(!movieRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie){
        if(!movieRepository.existsById(id)){
            ResponseEntity.notFound().build();
        }
        updatedMovie.setId(id);
        Movie savedMovie = movieRepository.save(updatedMovie);
        return ResponseEntity.ok(savedMovie);
    }

    @CrossOrigin
    @GetMapping("/vote/{id}/{rating}")
    public ResponseEntity<Movie> voteMovie(@PathVariable Long id, @PathVariable double rating){
        if(!movieRepository.existsById(id)){
            ResponseEntity.notFound().build();
        }
        Optional<Movie> optional = movieRepository.findById(id);
        Movie movie = optional.get();

        double newRating = ((movie.getVotes() * movie.getRating()) + rating) / (movie.getVotes() + 1);

        movie.setVotes(movie.getVotes() + 1);
        movie.setRating(newRating);

        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);
    }
}
