package BLL;

import BE.Category;
import BE.Movie;
import COMMON.ApplicationException;
import DAL.CatMoviesLogic.SelectCatMovies;
import DAL.CategoryLogic.SelectCategory;
import DAL.MovieLogic.InsertMovie;
import DAL.MovieLogic.SelectMovie;

import java.util.ArrayList;

public class BLLSingleton {
    // Single instance of GUISingleton
    private static final BLLSingleton instance = new BLLSingleton();

    // Global States
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();


    // DAL Ini
    private final SelectCategory selectCategory = new SelectCategory();
    private final SelectMovie selectMovie = new SelectMovie();
    private final SelectCatMovies selectCatMovies = new SelectCatMovies();

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
                c.setMovieIds(selectCatMovies.getCatMovieListDB(c.getId()));
                System.out.println("Category id"+c.getId()+"Movie id"+c.getMovieIds());
            }
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer -> singleton", e);
        }
    }


    public static BLLSingleton getInstance() {
        return instance;
    }


    public ArrayList<Category> getCategories(){
        return categories;
    }
    public void addCategory(Category newCategory){
        categories.add(newCategory);
    }

    public ArrayList<Movie> getMovies() {return movies;}
}
