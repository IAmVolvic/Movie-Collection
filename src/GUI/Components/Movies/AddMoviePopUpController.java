package GUI.Components.Movies;

import BE.Category;
import BLL.MovieService;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddMoviePopUpController {
    // BLL Services
    private final MovieService movieService = new MovieService();


    @FXML
    private TextField movieRatingInput;
    @FXML
    private TextField movieNameInput;
    @FXML
    private MFXTableView<Category> categoryTableView;


    public void ini(MFXTableView<Category> catTableView){
        categoryTableView = catTableView;
    }

    @FXML
    private void selectMovie(ActionEvent actionEvent) {
    }

    @FXML
    private void addMovieAccept(ActionEvent actionEvent) {
        System.out.println(categoryTableView.getSelectionModel().getSelectedValue().getId());
        movieService.createNewMovie(movieNameInput.getText(), "/helloworld");

    }

}
