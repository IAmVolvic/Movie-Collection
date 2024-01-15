package GUI.Components.Category;

import BE.Category;
import BLL.CategoryService;
import GUI.Components.Movies.MovieTable;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoryPopUpController {
    // BLL Services
    private CategoryService categoryService;

    // FXML Elements
    @FXML
    private MFXTableView<Category> categoriesTableView;
    @FXML
    private TextField categoryNameInput;


    public void ini(MFXTableView<Category> tableViewIni, CategoryService cs) {
        this.categoryService = cs;
        this.categoriesTableView = tableViewIni;
    }


    @FXML
    private void addCategoryAccept(ActionEvent actionEvent) {
        if (!categoryNameInput.getText().isEmpty()){
            categoryService.createNewCategory(categoryNameInput.getText());

            ObservableList<Category> newCategoryList = FXCollections.observableArrayList();
            newCategoryList.addAll(categoryService.getCategories());

            categoriesTableView.setItems(newCategoryList);
            categoriesTableView.update();

            Stage stage = (Stage) categoryNameInput.getScene().getWindow();
            stage.close();
        }
    }
}
