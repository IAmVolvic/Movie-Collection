package BLL;

import BE.Category;
import BE.Movie;
import BE.Remind;
import COMMON.ApplicationException;
import DAL.CatMoviesLogic.DeleteCat;
import DAL.CatMoviesLogic.InsertCat;
import DAL.CatMoviesLogic.SelectCat;
import DAL.CategoryLogic.SelectCategory;
import DAL.MovieLogic.DeleteMovie;
import DAL.MovieLogic.SelectMovie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


public class BLLSingleton {
    // Single instance of GUISingleton
    private static final BLLSingleton instance = new BLLSingleton();

    // Global States
    private ArrayList<Category> categories;
    private ArrayList<Movie> movies;
    private final ArrayList<Remind> reminds = new ArrayList<>();

    // DAL Ini
    private final SelectCategory selectCategory = new SelectCategory();
    private final SelectMovie selectMovie = new SelectMovie();
    private final SelectCat selectCat = new SelectCat();
    private final DeleteMovie deleteMovie = new DeleteMovie();
    private final DeleteCat deleteCat = new DeleteCat();
    private final InsertCat insertCat = new InsertCat();


    // Private constructor to prevent instantiation from outside
    private BLLSingleton() {
        initialize();
    }


    private void initialize() {
        try {
            categories = selectCategory.getCategoryDB();
            movies = selectMovie.getMoviesDB();

            for (Category c: categories) {
                c.setMovieIds(selectCat.getCatMovieListDB(c.getId()));
            }

            // Get old / low rated movies
            for(Movie m: movies){
                if (m.getRating() < 6 || isOldMovie(m.getLastViewed())){
                    Remind newReminder = new Remind(m.getId(), m.getName(), m.getRating(), m.getLastViewed());
                    reminds.add(newReminder);

                    for (Category cVal: categories) {
                        Optional<Integer> movieFilter = cVal.getMovieIds().stream()
                                .filter(movieId -> movieId == m.getId())
                                .findFirst();

                        movieFilter.ifPresent(c -> {
                            newReminder.addCategory(cVal.getName());
                        });
                    }
                }
            }

        } catch (ApplicationException | ParseException e) {
            throw new RuntimeException("Error in BLL layer -> singleton", e);
        }
    }


    private boolean isOldMovie(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Assuming date format is "yyyy-MM-dd"

        Date lastViewedDate = dateFormat.parse(date);
        Date currentDate = new Date();

        // Calculate the time difference in milliseconds
        long timeDifference = currentDate.getTime() - lastViewedDate.getTime();

        // Convert milliseconds to years
        long yearsDifference = timeDifference / (365 * 24 * 60 * 60 * 1000);

        // Check if the time difference is more than 3 years
        return yearsDifference >= 3;
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

    public void editMovie(Movie oldMovie, Movie newMovie){
        for (Movie m: movies){
            if (m == oldMovie){
                m.setName(newMovie.getName());
                m.setRating(newMovie.getRating());
            }
        }
    }

    public void editMovieCategoryRelation(ArrayList<String> addToCategory, int id) throws ApplicationException {

        for (String s:addToCategory) {
            Optional<Category> categoryFilter = categories.stream()
                    .filter(catVal -> catVal.getName().equals(s))
                    .findFirst();

            categoryFilter.ifPresent(c -> {
                Optional<Integer> movieIdsFilter = c.getMovieIds().stream()
                        .filter(movId -> movId == id)
                        .findFirst();
                if (movieIdsFilter.isEmpty()) {
                    // ID not found, so you can print "Hello"
                    System.out.println("Hello");
                    c.addToMovieIds(id);
                    try {
                        insertCat.newCat(id, c.getId());
                    } catch (ApplicationException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        cleanUpCategory(addToCategory, id);
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

    private void cleanUpCategory(ArrayList<String> addToCategory, int id) throws ApplicationException {
        ArrayList<Category> toDelete = new ArrayList<>();
        toDelete.addAll(categories);
        for (String s:addToCategory) {
            Optional<Category> categoryFilter = categories.stream()
                    .filter(catVal -> catVal.getName().equals(s))
                    .findFirst();
            categoryFilter.ifPresent(category -> {
                toDelete.remove(category);
            }
            );
        }
        for (Category c:toDelete) {
            for (int i:c.getMovieIds()) {
                if (id == i){
                    System.out.println(id);
                    c.deleteFromMovieIds(id);
                    deleteCat.removeCatMovieRelation(c.getId(),id);
                    break;
                }
            }
        }

    }

    public ArrayList<Remind> getReminds(){ return reminds; }
}
