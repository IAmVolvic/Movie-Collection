package GUI.Components.Category;

import BE.Category;
import BLL.CategoryService;
import GUI.Components.ErrorPopUpController;
import GUI.Components.Movies.MovieTable;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCategoryPopUpController {
    // BLL Services
    private CategoryService categoryService;

    // GUI Services
    private final ErrorPopUpController errorPopUp = new ErrorPopUpController();

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
    private void addCategoryAccept(ActionEvent actionEvent) throws IOException {
        if (!categoryNameInput.getText().isEmpty()){
            categoryService.createNewCategory(categoryNameInput.getText());

            ObservableList<Category> newCategoryList = FXCollections.observableArrayList();
            newCategoryList.addAll(categoryService.getCategories());

            categoriesTableView.setItems(newCategoryList);
            categoriesTableView.update();

            Stage stage = (Stage) categoryNameInput.getScene().getWindow();
            stage.close();
        }else{
            errorPopUp.prompError(
                    "Something went wrong, please check the following \n" +
                            "Make sure you a category name"
            );
        }
    }
}
