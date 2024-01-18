package GUI.Components.Movies;

import BE.Category;
import BE.Movie;
import BLL.FilePromptService;
import BLL.MovieService;
import GUI.Components.ErrorPopUpController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AddMoviePopUpController {
    // BLL Services
    private final MovieService movieService = new MovieService();
    private final FilePromptService fileService = new FilePromptService();

    // GUI Services
    private final ErrorPopUpController errorPopUp = new ErrorPopUpController();
    @FXML
    private MenuButton categoriesMenubtn;
    @FXML
    private MFXButton selecbtn;

    // FXML inputs
    @FXML
    private TextField movieRatingInput;
    @FXML
    private TextField movieNameInput;
    private ArrayList<String> addToCategory = new ArrayList<>();
    private ArrayList<Category> categories;
    @FXML
    private Runnable selectedObject;

    //Verbs
    private String filePath;
    private Movie editedMovie;
    private boolean editing;

    public void init(Runnable runnable, ArrayList<Category> categories){
        editing=false;
        this.selectedObject = runnable;
        this.categories = categories;
        setMenuBtnLogic();
    }


    @FXML
    private void selectMovie(ActionEvent actionEvent) {
        filePath = fileService.promptFilerChooser(actionEvent);
    }

    @FXML
    private void addMovieAccept(ActionEvent actionEvent) throws IOException {
        if (!editing){
            // Checks
            if (addToCategory.isEmpty() || movieNameInput.getText() == null || movieNameInput.getText().trim().isEmpty() || filePath == null) {
                errorPopUp.prompError(
                        "Something went wrong, please check the following \n" +
                                "1, Make sure you have the category selected \n" +
                                "2, Make sure the movie name is set \n" +
                                "3, Make sure you have selected a movie \n"
                );
                return;
            }



            double movieRating = (movieRatingInput.getText() == null || movieRatingInput.getText().trim().isEmpty() || !movieRatingInput.getText().matches("-?\\d+(\\.\\d+)?")) ? 0 : Double.parseDouble(movieRatingInput.getText());
            if (movieRating < 0 || movieRating > 10){
                errorPopUp.prompError(
                        "Something went wrong, please check the following \n" +
                                "Make sure you have the rating no lower then 0 and no higher then 10"
                );

                return;
            };
            movieService.createNewMovie(movieNameInput.getText(), filePath, movieRating, addToCategory,categories);
            selectedObject.run();

            // Close prompt
            Stage stage = (Stage) movieRatingInput.getScene().getWindow();
            stage.close();
        }else {
            if (movieNameInput.getText() == null || movieNameInput.getText().trim().isEmpty()){
                errorPopUp.prompError(
                        "Something went wrong, please check the following \n" +
                                "Make sure you have a movie name"
                );
                return;
            }
            double movieRating = (movieRatingInput.getText() == null || movieRatingInput.getText().trim().isEmpty() || !movieRatingInput.getText().matches("-?\\d+(\\.\\d+)?")) ? 0 : Double.parseDouble(movieRatingInput.getText());
            if (movieRating < 0 || movieRating > 10){
                errorPopUp.prompError(
                        "Something went wrong, please check the following \n" +
                                "Make sure you have the rating no lower then 0 and no higher then 10"
                );

                return;
            };
            movieService.editMovie(editedMovie,new Movie(editedMovie.getId(),movieNameInput.getText(),movieRating,editedMovie.getMoviePath(),editedMovie.getLastViewed()),addToCategory);
            selectedObject.run();
            // Close prompt
            Stage stage = (Stage) movieRatingInput.getScene().getWindow();
            stage.close();
        }
    }

    public void editMovieInit(Runnable runnabble, Movie movie,ArrayList<Category> categories){
        this.categories = categories;
        this.selectedObject = runnabble;
        editing=true;
        editedMovie = movie;
        selecbtn.disableProperty().set(true);
        movieRatingInput.setText(String.valueOf(movie.getRating()));
        movieNameInput.setText(movie.getName());
        setMenuBtnLogic();
    }

    private void setMenuBtnLogic(){
        categoriesMenubtn.getItems().clear();
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if (((CheckMenuItem)e.getSource()).isSelected()){
                    addToCategory.add((((CheckMenuItem)e.getSource()).getText()));
                }else {
                    addToCategory.remove((((CheckMenuItem)e.getSource()).getText()));
                }
            }
        };
        for (Category c:categories) {
            CheckMenuItem item = new CheckMenuItem(c.getName());
            categoriesMenubtn.getItems().add(item);
            item.setOnAction(event1);
            if(editing){
                checkMovieCategoryRelation(editedMovie,c,item,addToCategory);
            }
        }
    }

    private void checkMovieCategoryRelation(Movie movie, Category c, CheckMenuItem item, ArrayList<String> addToCategory){
            for (int id:c.getMovieIds()) {
                if (movie.getId() == id){
                    item.setSelected(true);
                    addToCategory.add(c.getName());
                }
            }
    }
}
