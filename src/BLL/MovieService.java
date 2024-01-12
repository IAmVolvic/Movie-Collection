package BLL;


import BE.Movie;
import COMMON.ApplicationException;
import DAL.MovieLogic.InsertMovie;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class MovieService {
    private final BLLSingleton single = BLLSingleton.getInstance();
    private final InsertMovie insertMovie = new InsertMovie();



    // Create a new category
    public Movie createNewMovie(String movieName, String moviePath) {
        try {
            Movie createMovie = insertMovie.newMovie(movieName, moviePath);

            return createMovie;
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer", e);
        }
    }

    public ObservableList<Movie> getMovies(){ return single.getMovies(); }
}
