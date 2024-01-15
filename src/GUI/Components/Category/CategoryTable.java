package GUI.Components.Category;

import BE.Category;
import BE.Movie;
import BLL.CategoryService;
import GUI.Components.ConfirmPopUpController;
import GUI.Components.Movies.MovieTable;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;


public class CategoryTable {
    // Services
    private CategoryService categoryService;
    private MovieTable movieTable;

    // FXM Elements
    @FXML
    private MFXTableView<Category> categoriesTableView;

    // Tables
    private final ObservableList<Category> categories = FXCollections.observableArrayList();

    public void ini(CategoryService iniCategoriesService, MFXTableView<Category> tableView, MovieTable movieTableComponent){
        categoryService = iniCategoriesService;
        categories.addAll(iniCategoriesService.getCategories());
        categoriesTableView = tableView;
        movieTable = movieTableComponent;

        Platform.runLater(this::setupTable);
    }

    private void setupTable() {
        MFXTableColumn<Category> categoryColumn = new MFXTableColumn<>("Category", false, Comparator.comparing(Category::getName));

        categoryColumn.setRowCellFactory(category -> new MFXTableRowCell<>(Category::getName));
        categoryColumn.setPrefWidth(270);

        categoriesTableView.getTableColumns().add(categoryColumn);
        categoriesTableView.getFilters().add(new StringFilter<>("Category", Category::getName));
        categoriesTableView.setItems(categories);

        categoriesTableView.setTableRowFactory( tv -> {
            MFXTableRow<Category> row = new MFXTableRow<>(categoriesTableView, tv);

            row.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                onRowClick(row);
            });

            return row;
        });

        if (!categoriesTableView.getItems().isEmpty()) {
            Category firstRow = categoriesTableView.getItems().get(0);
            categoriesTableView.getSelectionModel().selectItem(firstRow);
            movieTable.setSelectedCategory(firstRow);
        }
    }


    private void onRowClick(MFXTableRow<Category> row){
        if (row.getData()!=null){
            movieTable.setSelectedCategory(row.getData());
        }
    }

    public void promptAddCategory() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCategoryPopUp.fxml"));
        Parent root = loader.load();

        AddCategoryPopUpController controller = loader.getController();
        controller.ini(categoriesTableView, categoryService);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Add category");
        primaryStage.show();
    }

    public void promptEditCategory() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditCategoryPopUp.fxml"));
        Parent root = loader.load();
        EditCategoryPopUpController controller = loader.getController();

        controller.ini(categoriesTableView, categoryService);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Edit category");
        primaryStage.show();
    }


    public void promptDeleteCategory() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConfirmPopUp.fxml"));
        Parent root = loader.load();
        ConfirmPopUpController controller = loader.getController();

        controller.init(this::deleteCategory);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Delete category");
        primaryStage.show();
    }


    private void deleteCategory() {
        if (categoriesTableView.getSelectionModel().getSelectedValue() != null){
            categoryService.deleteCategory(categoriesTableView.getSelectionModel().getSelectedValue());

            ObservableList<Category> newCategoryList = FXCollections.observableArrayList();
            newCategoryList.addAll(categoryService.getCategories());

            categoriesTableView.setItems(newCategoryList);
            categoriesTableView.update();

            movieTable.clearTable();
            movieTable.setSelectedCategory(null);
        }
    }
}
