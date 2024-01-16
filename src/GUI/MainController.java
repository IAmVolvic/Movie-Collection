package GUI;

import BE.Category;
import BE.Movie;
import BLL.CategoryService;
import BLL.MovieService;
import GUI.Components.Category.CategoryTable;
import GUI.Components.MovieInfoController;
import GUI.Components.Movies.MovieTable;
import GUI.Components.ReminderController;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    private ReminderController reminderController = new ReminderController();

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
                movieInfoController,
                categoryService
        );

        try {
            showReminder();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void showReminder() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReminderPrompt.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Reminder");
        stage.show();
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
    private void playMovie(ActionEvent actionEvent) throws IOException {
        movieInfoController.playMovie();
    }

    @FXML
    private void editMoviePrompt(ActionEvent actionEvent) throws IOException {
        movieTableComponent.promptEditMovie();
    }
}
