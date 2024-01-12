package GUI;

import BE.Category;
import BE.Movie;
import BLL.CategoryService;
import BLL.MovieService;
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
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class MainController implements Initializable {
    // BLL Services
    private final MovieService movieService = new MovieService();

    private final CategoryService categoryService = new CategoryService();

    @FXML
    private MFXTableView<Category> categoriesTableView;
    @FXML
    private MFXTableView<Movie> moviesTableView;

    //Instance variables
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private Category selectedCategory;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories = categoryService.getCategories();
        movies.add(null);
        setupTableCategory();
        setupTableMovies();
    }

    private void setupTableCategory() {
        MFXTableColumn<Category> categoryColumn = new MFXTableColumn<>("Category", false, Comparator.comparing(Category::getName));

        categoryColumn.setRowCellFactory(category -> new MFXTableRowCell<>(Category::getName));
        categoryColumn.setPrefWidth(270);

        categoriesTableView.getTableColumns().add(categoryColumn);

        categoriesTableView.setItems(categories);

        selectedCategory = categoriesTableView.getSelectionModel().getSelectedValue();

        categoriesTableView.setTableRowFactory( tv -> { //doesnt work
            MFXTableRow<Category> row = new MFXTableRow<>(categoriesTableView, tv);

            row.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                if (row.getData()!=null){
                    movies.clear();
                    for (int id:row.getData().getMovieIds()) {
                        for (Movie m : movieService.getMovies()) {
                            if (m.getId() == id) {
                                movies.add(m);
                                break;
                            }
                        }
                    }
                    moviesTableView.update();
                }
            });


            return row;
        });
    }

    private void setupTableMovies() {
        MFXTableColumn<Movie> idColumn = new MFXTableColumn<>("Id", false, Comparator.comparing(Movie::getId));
        MFXTableColumn<Movie> titleColumn = new MFXTableColumn<>("Title", false, Comparator.comparing(Movie::getName));
        MFXTableColumn<Movie> ratingColumn = new MFXTableColumn<>("Rating", false,
                Comparator.comparing(Movie::getRating));
        MFXTableColumn<Movie> lastViewed = new MFXTableColumn<>("Last Viewed", false,
                Comparator.comparing(Movie::getLastViewed));
        idColumn.setRowCellFactory(movie -> new MFXTableRowCell<>(Movie::getId));
        titleColumn.setRowCellFactory(movie -> new MFXTableRowCell<>(Movie::getName));
        ratingColumn.setRowCellFactory(movie -> new MFXTableRowCell<>(Movie::getRating));
        lastViewed.setRowCellFactory(movie -> new MFXTableRowCell<>(Movie::getLastViewed));
        moviesTableView.getTableColumns().add(idColumn);
        moviesTableView.getTableColumns().add(titleColumn);
        moviesTableView.getTableColumns().add(ratingColumn);
        moviesTableView.getTableColumns().add(lastViewed);
        moviesTableView.setItems(movies);
    }

    @FXML
    private void addCategoryHandler(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCategoryPopUp.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Add category");
        primaryStage.show();
    }

    public void Debug(ActionEvent actionEvent) {
       for(Category value : categoryService.getCategories()){
           System.out.println(value.getName());
       }
    }
    @FXML
    private void deleteCat(ActionEvent actionEvent) {
        selectedCategory = categoriesTableView.getSelectionModel().getSelectedValue();
        if (selectedCategory!=null){
            categoryService.deleteCategory(selectedCategory);
        }
    }
    @FXML
    private void editCategory(ActionEvent actionEvent) throws IOException {
        selectedCategory = categoriesTableView.getSelectionModel().getSelectedValue();
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditCategoryPopUp.fxml"));
        Parent root = loader.load();
        EditCategoryPopUpController controller = loader.getController();
        controller.setTextField(selectedCategory,categoryService,this);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Edit category");
        primaryStage.show();
    }

    public void updateTable(){
        categoriesTableView.update();
    }
}
