package GUI.Components.Movies;

import BE.Category;
import BE.Movie;
import BLL.FilePromptService;
import BLL.MovieService;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMoviePopUpController {
    // BLL Services
    private final MovieService movieService = new MovieService();
    private final FilePromptService fileService = new FilePromptService();

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


    public void init(Runnable runnable, Category SC){
        this.selectedObject = runnable;
        selectedCategory = SC;
    }


    @FXML
    private void selectMovie(ActionEvent actionEvent) {
        filePath = fileService.promptFilerChooser(actionEvent);
    }

    @FXML
    private void addMovieAccept(ActionEvent actionEvent) {
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
    }

}
