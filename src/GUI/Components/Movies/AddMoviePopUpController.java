package GUI.Components.Movies;

import BE.Category;
import BE.Movie;
import BLL.FilePromptService;
import BLL.MovieService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMoviePopUpController {
    // BLL Services
    private final MovieService movieService = new MovieService();
    private final FilePromptService fileService = new FilePromptService();

    @FXML
    private MFXButton selecbtn;

    // FXML inputs
    @FXML
    private TextField movieRatingInput;
    @FXML
    private TextField movieNameInput;
    @FXML
    private Category selectedCategory;
    @FXML
    private Runnable selectedObject;

    //Verbs
    private String filePath;
    private Movie editedMovie;
    private boolean editing;

    public void init(Runnable runnable, Category SC){
        editing=false;
        this.selectedObject = runnable;
        selectedCategory = SC;
    }


    @FXML
    private void selectMovie(ActionEvent actionEvent) {
        filePath = fileService.promptFilerChooser(actionEvent);
    }

    @FXML
    private void addMovieAccept(ActionEvent actionEvent) {
        if (!editing){
            // Checks
            if (selectedCategory == null || selectedCategory.getId() < 1 || movieNameInput.getText() == null || movieNameInput.getText().trim().isEmpty() || filePath == null) {
                System.out.println("Something went wrong");
                return;
            }

            double movieRating = (movieRatingInput.getText() == null || movieRatingInput.getText().trim().isEmpty() || !movieRatingInput.getText().matches("-?\\d+(\\.\\d+)?")) ? 0 : Double.parseDouble(movieRatingInput.getText());
            if (movieRating < 0 || movieRating > 10){ System.out.println("Rating Malformed"); return; };

            movieService.createNewMovie(movieNameInput.getText(), filePath, movieRating, selectedCategory.getId());
            selectedObject.run();

            // Close prompt
            Stage stage = (Stage) movieRatingInput.getScene().getWindow();
            stage.close();
        }else {
            if (movieNameInput.getText() == null || movieNameInput.getText().trim().isEmpty()){
                System.out.println("Something went wrong");
                return;
            }
            double movieRating = (movieRatingInput.getText() == null || movieRatingInput.getText().trim().isEmpty() || !movieRatingInput.getText().matches("-?\\d+(\\.\\d+)?")) ? 0 : Double.parseDouble(movieRatingInput.getText());
            if (movieRating < 0 || movieRating > 10){ System.out.println("Rating Malformed"); return; };
            movieService.editMovie(editedMovie,new Movie(editedMovie.getId(),movieNameInput.getText(),movieRating,editedMovie.getMoviePath(),editedMovie.getLastViewed()));
            selectedObject.run();
            // Close prompt
            Stage stage = (Stage) movieRatingInput.getScene().getWindow();
            stage.close();
        }
    }
    public void editMovieInit(Runnable runnabble, Movie movie){
        this.selectedObject = runnabble;
        editing=true;
        editedMovie = movie;
        selecbtn.disableProperty().set(true);
        movieRatingInput.setText(String.valueOf(movie.getRating()));
        movieNameInput.setText(movie.getName());
    }
}
