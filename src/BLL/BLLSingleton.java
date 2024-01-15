package BLL;

import BE.Category;
import BE.Movie;
import COMMON.ApplicationException;
import DAL.CatMoviesLogic.DeleteCat;
import DAL.CatMoviesLogic.SelectCat;
import DAL.CategoryLogic.SelectCategory;
import DAL.MovieLogic.DeleteMovie;
import DAL.MovieLogic.SelectMovie;

import java.util.ArrayList;
import java.util.Optional;


public class BLLSingleton {
    // Single instance of GUISingleton
    private static final BLLSingleton instance = new BLLSingleton();

    // Global States
    private ArrayList<Category> categories;
    private ArrayList<Movie> movies;


    // DAL Ini
    private final SelectCategory selectCategory = new SelectCategory();
    private final SelectMovie selectMovie = new SelectMovie();
    private final SelectCat selectCat = new SelectCat();
    private final DeleteMovie deleteMovie = new DeleteMovie();
    private final DeleteCat deleteCat = new DeleteCat();


    // Private constructor to prevent instantiation from outside
    private BLLSingleton() {
        // Initialize
        categoriesInitialize();
    }


    private void categoriesInitialize() {
        try {
            categories = selectCategory.getCategoryDB();
            movies = selectMovie.getMoviesDB();

            for (Category c: categories) {
                c.setMovieIds(selectCat.getCatMovieListDB(c.getId()));
            }
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer -> singleton", e);
        }
    }


    public static BLLSingleton getInstance() {
        return instance;
    }


    // Category Services
    public ArrayList<Category> getCategories(){
        return categories;
    }
    public void addCategory(Category newCategory){
        categories.add(newCategory);
    }

    public void deleteCategory(Category category){
        categories.remove(category);
    }

    public void editCategory(String newCategory, int oldCategory){
        for (Category c:categories) {
            if (c.getId() == oldCategory){
                c.setName(newCategory);
            }
        }
    }


    // Movie Services
    public ArrayList<Movie> getMovies() {return movies;}

    public void newMovie(Movie movie) { movies.add(movie); }

    public void addMovieToCategory(int categoryId, int movieId) {
        Optional<Category> movieIdsTable = categories.stream()
                .filter(category -> category.getId() == categoryId)
                .findFirst();

        movieIdsTable.ifPresent(movie -> movie.getMovieIds().add(movieId));
    }


    // Common Between Move and Category Services
    public void deleteMoviesAndCategory(Category selectedCategory) {
        try {
            for( int val : selectedCategory.getMovieIds()) {
                deleteMovie.removeMovie(val);

                Optional<Movie> movieData = movies.stream()
                        .filter(movieVal -> movieVal.getId() == val)
                        .findFirst();

                movieData.ifPresent(movie -> {
                    movies.remove(movie);
                });
            }

            deleteCat.removeCatByCategory(selectedCategory.getId());
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer -> singleton", e);
        }
    }

    public void deleteMovieFromCategory(Category selectedCategory, Movie selectedMovie) {
        try {
            deleteCat.removeCatByMovie(selectedMovie.getId());

            int indexOfGlobalCategory = categories.indexOf(selectedCategory);
            int indexOfMovie = selectedCategory.getMovieIds().indexOf(selectedMovie.getId());

            categories.get(indexOfGlobalCategory).getMovieIds().remove(indexOfMovie);
            movies.remove(selectedMovie);
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer -> singleton", e);
        }
    }

}
