package GUI;

import BE.Category;
import BE.Movie;
import BLL.CategoryService;
import BLL.MovieService;
import GUI.Components.Category.CategoryTable;
import GUI.Components.Category.EditCategoryPopUpController;
import GUI.Components.Movies.MovieTable;
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
    private final MovieTable movieTableComponent = new MovieTable();


    // FXML Elements
    @FXML
    private MFXTableView<Category> categoriesTableView;
    @FXML
    private MFXTableView<Movie> moviesTableView;


    //Instance variables


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTableComponent.ini(
                movieService,
                moviesTableView
        );

        categoryTableComponent.ini(
                categoryService,
                categoriesTableView,
                movieTableComponent
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
    private void deleteCat(ActionEvent aE) {
        categoryTableComponent.deleteCategory();
    }



    public void Debug(ActionEvent aE) {
       for(Category value : categoryService.getCategories()){
           System.out.println(value.getName());
       }
    }
}
