package BLL;

import BE.Category;
import BE.Movie;
import COMMON.ApplicationException;
import DAL.CatMoviesLogic.SelectCatMovies;
import DAL.CategoryLogic.SelectCategory;
import DAL.MovieLogic.InsertMovie;
import DAL.MovieLogic.SelectMovie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class BLLSingleton {
    // Single instance of GUISingleton
    private static final BLLSingleton instance = new BLLSingleton();

    // Global States
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private ObservableList<Movie> movies = FXCollections.observableArrayList();


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
            }
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer -> singleton", e);
        }
    }


    public static BLLSingleton getInstance() {
        return instance;
    }

    public ObservableList<Category> getCategories(){
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
                System.out.println(c.getName());
            }
        }
    }

    public ObservableList<Movie> getMovies() {return movies;}

    private void categoriesObserver(){}
}
