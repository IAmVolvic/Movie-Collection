package GUI;

import BE.Category;
import BE.Movie;
import BLL.CategoryService;
import BLL.MovieService;
import GUI.Components.Category.CategoryTable;
import GUI.Components.MovieInfoController;
import GUI.Components.Movies.MovieTable;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    // BLL Services
    private final MovieService movieService = new MovieService();
    private final CategoryService categoryService = new CategoryService();


    // Components
    private final CategoryTable categoryTableComponent = new CategoryTable();
    private final MovieTable movieTableComponent = new MovieTable();
    private MovieInfoController movieInfoController;

    // FXML Elements
    @FXML
    private MFXTableView<Category> categoriesTableView;
    @FXML
    private MFXTableView<Movie> moviesTableView;
    @FXML
    private Label movieTitle;
    @FXML
    private Label movieRating;

    //Instance variables


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieInfoController = new MovieInfoController(
                movieTitle,
                movieRating
        );

        categoryTableComponent.ini(
                categoryService,
                categoriesTableView,
                movieTableComponent
        );

        movieTableComponent.ini(
                movieService,
                moviesTableView,
                movieInfoController
        );
    }




    // Category Service Methods
    @FXML
    private void addCategoryHandler(ActionEvent aE) throws IOException {
        categoryTableComponent.promptAddCategory();
    }

    @FXML
    private void editCategory(ActionEvent aE) throws IOException {
        categoryTableComponent.promptEditCategory();
    }

    @FXML
    private void deleteCategory(ActionEvent aE) throws IOException {
        categoryTableComponent.promptDeleteCategory();
    }


    // Movie Service Methods
    @FXML
    private void addMoviePrompt(ActionEvent aE) throws IOException {
        movieTableComponent.promptAddMovie();
    }

    @FXML
    private void deleteMovie(ActionEvent actionEvent) throws IOException {
        movieTableComponent.promptDeleteMovie();
    }

    @FXML
    private void playMovie(ActionEvent actionEvent) {
        movieInfoController.playMovie();
    }
}
