package GUI;

import BE.Category;
import BE.Movie;
import BLL.CategoryService;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


public class MainController implements Initializable {
    // BLL Services
    private final CategoryService categoryService = new CategoryService();

    @FXML
    private MFXTableView<Category> categoriesTableView;
    @FXML
    private MFXTableView<Movie> moviesTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableCategory();
        setupTableMovies();
    }

    private void setupTableCategory() {
        MFXTableColumn<Category> categoryColumn = new MFXTableColumn<>("Category", false, Comparator.comparing(Category::getName));
        categoryColumn.setRowCellFactory(category -> new MFXTableRowCell<>(Category::getName));
        categoryColumn.setPrefWidth(270);
        categoriesTableView.getTableColumns().add(categoryColumn);
        categoriesTableView.getFilters().add(
                new StringFilter<>("Category", Category::getName)
        );
    }

    private void setupTableMovies(){
        MFXTableColumn<Movie> idColumn = new MFXTableColumn<>("Id",false,Comparator.comparing(Movie::getId));
        MFXTableColumn<Movie> titleColumn = new MFXTableColumn<>("Title",false,Comparator.comparing(Movie::getName));
        MFXTableColumn<Movie> ratingColumn = new MFXTableColumn<>("Rating",false,Comparator.comparing(Movie::getRating));
        MFXTableColumn<Movie> lastViewed = new MFXTableColumn<>("Last Viewed",false,Comparator.comparing(Movie::getLastViewed));
        idColumn.setRowCellFactory(movie -> new MFXTableRowCell<Movie, Object>(Movie::getId));
        titleColumn.setRowCellFactory(movie -> new MFXTableRowCell<Movie, Object>(Movie::getName));
        ratingColumn.setRowCellFactory(movie -> new MFXTableRowCell<Movie, Object>(Movie::getRating));
        lastViewed.setRowCellFactory(movie -> new MFXTableRowCell<Movie, Object>(Movie::getLastViewed));
        moviesTableView.getTableColumns().addAll(idColumn,titleColumn,ratingColumn,lastViewed);
    }

    public void testRunVideo(ActionEvent actionEvent) {


        System.out.println(categoryService.getCategories());


//        try {
////            categoryService.createNewCategory("New Category");
//        } catch (RuntimeException e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getCause().getMessage());
//        }

//        System.out.println("Running Video");
//
//        String videoPath = "resources/assets/testVideo.mp4";
//
//        try {
//            File videoFile = new File(videoPath);
//            Desktop.getDesktop().open(videoFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
