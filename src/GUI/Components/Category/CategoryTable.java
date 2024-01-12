package GUI.Components.Category;

import BE.Category;
import BLL.CategoryService;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;


public class CategoryTable {
    // Services
    private CategoryService categoryService;

    // FXM Elements
    @FXML
    private MFXTableView<Category> categoriesTableView;

    // Tables
    private ObservableList<Category> categories;

    public void ini(CategoryService iniCategoriesService, MFXTableView<Category> tableView){
        categoryService = iniCategoriesService;
        categories = iniCategoriesService.getCategories();
        categoriesTableView = tableView;
        setupTableCategory();
    }

    private void setupTableCategory() {
        MFXTableColumn<Category> categoryColumn = new MFXTableColumn<>("Category", false, Comparator.comparing(Category::getName));

        categoryColumn.setRowCellFactory(category -> new MFXTableRowCell<>(Category::getName));
        categoryColumn.setPrefWidth(270);

        categoriesTableView.getTableColumns().add(categoryColumn);
        categoriesTableView.getFilters().add(new StringFilter<>("Category", Category::getName));
        categoriesTableView.setItems(categories);

        categoriesTableView.setTableRowFactory( tv -> { //doesnt work
            MFXTableRow<Category> row = new MFXTableRow<>(categoriesTableView, tv);

            row.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                onRowCategoryClick(row);
            });

            return row;
        });
    }

    private void onRowCategoryClick(MFXTableRow<Category> row){
        if (row.getData()!=null){
            System.out.println("Hmm");
        }
    }


    public void promptAddCategory() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCategoryPopUp.fxml"));
        Parent root = loader.load();
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


    public void deleteCategory() {
        if (categoriesTableView.getSelectionModel().getSelectedValue() != null){
            categoryService.deleteCategory(categoriesTableView.getSelectionModel().getSelectedValue());
        }
    }

}
