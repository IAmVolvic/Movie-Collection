package GUI.Components.Movies;


import BE.Movie;
import BLL.MovieService;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.util.Comparator;

public class MovieTable {
    // Services
    private MovieService movieService;

    // FXM Elements
    @FXML
    private MFXTableView<Movie> movieTableView;

    // Tables
    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    public void ini(MovieService iniMovieService, MFXTableView<Movie> tableView){
        movieService = iniMovieService;
        movies.addAll(movieService.getMovies());
        movieTableView = tableView;
        setupTable();
    }

    public MFXTableView<Movie> getTable(){
        return this.movieTableView;
    }

    public ObservableList<Movie> getList(){
        return this.movies;
    }

    public MovieService getMovieService() {
        return this.movieService;
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
        movieTableView.setItems(movies);


        movieTableView.setTableRowFactory( tv -> { //doesnt work
            MFXTableRow<Movie> row = new MFXTableRow<>(movieTableView, tv);

            row.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                onRowClick(row);
            });

            return row;
        });
    }

    private void onRowClick(MFXTableRow<Movie> row){
        if (row.getData()!=null){
            System.out.println("Works");
        }
    }

}
