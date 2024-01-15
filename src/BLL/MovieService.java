package BLL;


import BE.Category;
import BE.Movie;
import COMMON.ApplicationException;
import DAL.CatMoviesLogic.InsertCat;
import DAL.MovieLogic.DeleteMovie;
import DAL.MovieLogic.InsertMovie;

import java.util.ArrayList;


public class MovieService {
    private final BLLSingleton single = BLLSingleton.getInstance();
    private final InsertMovie insertMovie = new InsertMovie();
    private final InsertCat insertCat = new InsertCat();
    private final DeleteMovie deleteMovie = new DeleteMovie();

    // Create a new category
    // Create a new link between category and movie
    public void createNewMovie(String movieName, String moviePath, Double movieRating, int categoryId) {
        try {
            Movie createMovie = insertMovie.newMovie(movieName, moviePath, movieRating);
            single.newMovie(createMovie);
            single.addMovieToCategory(categoryId, createMovie.getId());

            insertCat.newCat(createMovie.getId(), categoryId);
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer", e);
        }
    }

    // Delete a movie
    public void deleteMove(Category selectedCategory, Movie selectedMovie) {
        try {
            // Delete the selected movie from the DB
            deleteMovie.removeMovie(selectedMovie.getId());

            // Remove the movie from Cat DB and the globals
            single.deleteMovieFromCategory(selectedCategory, selectedMovie);
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer", e);
        }
    }

    // Add connection between move and category
    public ArrayList<Movie> getMovies(){ return single.getMovies(); }
}
