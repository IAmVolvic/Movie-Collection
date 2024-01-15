package GUI.Components;

import BLL.FilePromptService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class MovieInfoController {
    // BLL Services
    private final FilePromptService filePromptService = new FilePromptService();

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

    public void playMovie(){
        if(!moviePath.isEmpty()){
            filePromptService.playFile(this.moviePath);
        }
    }
}
