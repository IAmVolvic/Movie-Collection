package GUI.Components.Movies;


import BE.Category;
import BE.Movie;
import BLL.CategoryService;
import BLL.MovieService;
import GUI.Components.Category.CategoryTable;
import GUI.Components.ConfirmPopUpController;
import GUI.Components.MovieInfoController;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

public class MovieTable {
    // Services
    private MovieService movieService;
    private Category selectedCategory;
    private MovieInfoController movieInfoController;
    private CategoryService categoryService;


    // FXM Elements
    @FXML
    private MFXTableView<Movie> movieTableView;

    // Tables
    private final ObservableList<Movie> movies = FXCollections.observableArrayList();

    public void ini(MovieService iniMovieService, MFXTableView<Movie> tableView, MovieInfoController mIC, CategoryService service){
        movieService = iniMovieService;
        movieTableView = tableView;
        movieInfoController = mIC;
        categoryService = service;
        setupTable();
    }


    private void setupTable() {
        MFXTableColumn<Movie> idColumn = new MFXTableColumn<>("Id", false, Comparator.comparing(Movie::getId));
        MFXTableColumn<Movie> titleColumn = new MFXTableColumn<>("Title", false, Comparator.comparing(Movie::getName));
        MFXTableColumn<Movie> ratingColumn = new MFXTableColumn<>("Rating", false, Comparator.comparing(Movie::getRating));
        MFXTableColumn<Movie> lastViewed = new MFXTableColumn<>("Last Viewed", false, Comparator.comparing(Movie::getLastViewed));

        idColumn.setRowCellFactory(movie -> new MFXTableRowCell<Movie, Integer>(Movie::getId));
        titleColumn.setRowCellFactory(movie -> new MFXTableRowCell<Movie, String>(Movie::getName));
        ratingColumn.setRowCellFactory(movie -> new MFXTableRowCell<Movie, Double>(Movie::getRating));
        lastViewed.setRowCellFactory(movie -> new MFXTableRowCell<Movie, String>(Movie::getLastViewed));

        movieTableView.getFilters().add(new StringFilter<>("Title", Movie::getName));
        movieTableView.getFilters().add(new DoubleFilter<>("Rating", Movie::getRating));
        movieTableView.getFilters().add(new StringFilter<>("Last Viewed", Movie::getLastViewed));

        movieTableView.getTableColumns().add(idColumn);
        movieTableView.getTableColumns().add(titleColumn);
        movieTableView.getTableColumns().add(ratingColumn);
        movieTableView.getTableColumns().add(lastViewed);
    }


    private void recreateTable() {
        if (selectedCategory == null) { return; }
        
        // Ignore
        movieTableView.getItems().clear();

        ObservableList<Movie> newMovieList = FXCollections.observableArrayList();

        //Set the selected movie table
        for (Movie m : movieService.getMovies()) {
            for (int id : selectedCategory.getMovieIds()) {
                if (m.getId() == id) {
                    newMovieList.add(m);
                    break;
                }
            }
        }

        movieTableView.getItems().addAll(newMovieList);

        movieTableView.setTableRowFactory( tv -> { //doesnt work
            MFXTableRow<Movie> row = new MFXTableRow<>(movieTableView, tv);

            row.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                onRowClick(row);
            });

            return row;
        });

        movies.addAll(newMovieList);

        if (!movieTableView.getItems().isEmpty()) {
            Movie firstRow = movieTableView.getItems().get(0);

            movieInfoController.setTitle(firstRow.getName());
            movieInfoController.setRating( String.valueOf(firstRow.getRating()) );
            movieInfoController.setMoviePath(firstRow.getMoviePath());
        }
    }


    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
        recreateTable();
    }
    public void clearTable() {
        movieTableView.getItems().clear();
    }


    public void promptAddMovie() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMoviePopUp.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Add Movie");
        AddMoviePopUpController controller = loader.getController();
        controller.init(this::recreateTable, categoryService.getCategories());
        primaryStage.show();
    }


    public void promptDeleteMovie() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConfirmPopUp.fxml"));
        Parent root = loader.load();
        ConfirmPopUpController controller = loader.getController();

        controller.init(this::deleteMovie);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Delete movie");
        primaryStage.show();
    }

    public void promptEditMovie() throws IOException {
        if (movieTableView.getSelectionModel().getSelectedValue()!=null){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMoviePopUp.fxml"));
            Parent root = loader.load();
            AddMoviePopUpController controller = loader.getController();
            controller.editMovieInit( this::recreateTable,movieTableView.getSelectionModel().getSelectedValue(),categoryService.getCategories());
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Movie");
            stage.show();
        }
    }


    private void deleteMovie() {
        if (selectedCategory != null && movieTableView.getSelectionModel().getSelectedValue() != null){
            movieService.deleteMove(selectedCategory, movieTableView.getSelectionModel().getSelectedValue());
            recreateTable();
        }
    }



    private void onRowClick(MFXTableRow<Movie> row){
        if (row.getData()!=null){
            movieInfoController.setTitle(row.getData().getName());
            movieInfoController.setRating( String.valueOf(row.getData().getRating()) );
            movieInfoController.setMoviePath(row.getData().getMoviePath());
        }
    }

}
