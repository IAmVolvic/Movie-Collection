package BLL;


import BE.Category;
import BE.Movie;
import BE.Remind;
import COMMON.ApplicationException;
import DAL.CatMoviesLogic.InsertCat;
import DAL.MovieLogic.DeleteMovie;
import DAL.MovieLogic.EditMovie;
import DAL.MovieLogic.InsertMovie;

import java.util.ArrayList;
import java.util.Objects;


public class MovieService {
    private final BLLSingleton single = BLLSingleton.getInstance();
    private final InsertMovie insertMovie = new InsertMovie();
    private final InsertCat insertCat = new InsertCat();
    private final DeleteMovie deleteMovie = new DeleteMovie();
    private final EditMovie editMovie = new EditMovie();

    // Create a new category
    // Create a new link between category and movie
    public void createNewMovie(String movieName, String moviePath, Double movieRating, ArrayList<String> categoriesName, ArrayList<Category> categories) {
        try {
            Movie createMovie = insertMovie.newMovie(movieName, moviePath, movieRating);
            single.newMovie(createMovie);
            for (String s:categoriesName) {
                for (Category c:categories) {
                    if (Objects.equals(s, c.getName())){
                        single.addMovieToCategory(c.getId(), createMovie.getId());

                        insertCat.newCat(createMovie.getId(), c.getId());
                    }
                }
            }

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

    public void editMovie(Movie oldMovie, Movie newMovie, ArrayList<String> addToCategory){
        try {
            single.editMovie(oldMovie,newMovie);
            single.editMovieCategoryRelation(addToCategory,oldMovie.getId());
            editMovie.editMovie(oldMovie,newMovie);
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer", e);
        }
    }

    // Add connection between move and category
    public ArrayList<Movie> getMovies(){ return single.getMovies(); }


    // Get old movies / low rated movie
    public ArrayList<Remind> getRemindedMovies(){
        return single.getReminds();
    }
}
