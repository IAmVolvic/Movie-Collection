package GUI.Components;

import BLL.FilePromptService;
import COMMON.ApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class MovieInfoController {
    // BLL Services
    private final FilePromptService filePromptService = new FilePromptService();

    // GUI Services
    private final ErrorPopUpController errorPopUp = new ErrorPopUpController();

    // FXML Elements
    @FXML
    private Label movieTitle;
    @FXML
    private Label movieRating;

    // Verbs
    private String moviePath;


    // Constructor
    public MovieInfoController(Label movieLabel, Label movieRatingLabel) {
        movieTitle = movieLabel;
        movieRating = movieRatingLabel;
    }



    public void setTitle(String newTitle){
        this.movieTitle.setText(newTitle);
    }

    public void setRating(String newRating){
        this.movieRating.setText(newRating);
    }

    public void setMoviePath(String moviePath) {
        this.moviePath = moviePath;
    }

    public void playMovie() throws IOException {
        if(!moviePath.isEmpty()){
            try {
                filePromptService.playFile(this.moviePath);
            } catch (ApplicationException e) {
                // Display the error message
                errorPopUp.prompError(
                        "Something went wrong, please check the following:\n" +
                                "Cannot play this movie\n\n\n" +
                                e.getMessage() + "\n\n"
                );
            }
        }
    }
}
