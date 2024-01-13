package GUI.Components.Category;

import BE.Category;
import BLL.CategoryService;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCategoryPopUpController {
    // Services
    private CategoryService categoryService;

    // FXM Elements
    @FXML
    private MFXTableView<Category> categoriesTableView;
    @FXML
    private TextField categoryNamelbl;

    // Values
    private Category selectedRow;

    public void editCategoryAccept(ActionEvent actionEvent) {
        if (categoryNamelbl.getText()!=null){
            categoryService.changeCategory(categoryNamelbl.getText(), this.selectedRow.getId());
            categoriesTableView.update();

            Stage stage = (Stage) categoryNamelbl.getScene().getWindow();
            stage.close();
        }
    }

    public void ini(MFXTableView<Category> tableViewIni, CategoryService iniCategoriesService){
        this.categoriesTableView = tableViewIni;
        this.categoryService = iniCategoriesService;
        this.selectedRow = tableViewIni.getSelectionModel().getSelectedValue();


        categoryNamelbl.setText(this.selectedRow.getName());
    }
}
