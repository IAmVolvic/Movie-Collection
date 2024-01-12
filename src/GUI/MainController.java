package GUI;

import BE.Category;
import BE.Movie;
import BLL.CategoryService;
import BLL.MovieService;
import GUI.Components.Category.CategoryTable;
import GUI.Components.Category.EditCategoryPopUpController;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    // BLL Services
    private final MovieService movieService = new MovieService();
    private final CategoryService categoryService = new CategoryService();

    // Components
    private final CategoryTable categoryTableComponent = new CategoryTable();

    // FXML Elements
    @FXML
    private MFXTableView<Category> categoriesTableView;
    @FXML
    private MFXTableView<Movie> moviesTableView;

    //Instance variables
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private Category selectedCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryTableComponent.ini(
                categoryService,
                categoriesTableView
        );

        setupTableMovies();
    }


    private void setupTableMovies() {
        MFXTableColumn<Movie> idColumn = new MFXTableColumn<>("Id", false, Comparator.comparing(Movie::getId));
        MFXTableColumn<Movie> titleColumn = new MFXTableColumn<>("Title", false, Comparator.comparing(Movie::getName));
        MFXTableColumn<Movie> ratingColumn = new MFXTableColumn<>("Rating", false, Comparator.comparing(Movie::getRating));
        MFXTableColumn<Movie> lastViewed = new MFXTableColumn<>("Last Viewed", false, Comparator.comparing(Movie::getLastViewed));

        idColumn.setRowCellFactory(movie -> new MFXTableRowCell<Movie, Integer>(Movie::getId));
        titleColumn.setRowCellFactory(movie -> new MFXTableRowCell<Movie, String>(Movie::getName));
        ratingColumn.setRowCellFactory(movie -> new MFXTableRowCell<Movie, Double>(Movie::getRating));
        lastViewed.setRowCellFactory(movie -> new MFXTableRowCell<Movie, String>(Movie::getLastViewed));

        moviesTableView.getTableColumns().addAll(idColumn, titleColumn, ratingColumn, lastViewed);
        moviesTableView.setItems(movieService.getMovies());
    }



    @FXML
    private void addCategoryHandler(ActionEvent actionEvent) throws IOException {
        categoryTableComponent.promptAddCategory();
    }

    @FXML
    private void editCategory(ActionEvent actionEvent) throws IOException {
        categoryTableComponent.promptEditCategory();
    }

    @FXML
    private void deleteCat(ActionEvent actionEvent) {
        categoryTableComponent.deleteCategory();
    }



    public void Debug(ActionEvent actionEvent) {
       for(Category value : categoryService.getCategories()){
           System.out.println(value.getName());
       }
    }
}
