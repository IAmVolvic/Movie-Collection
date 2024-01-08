package GUI;

import BE.Category;
import BLL.BLLSingleton;
import BLL.CategoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCategoryPopUpController {
    @FXML
    private TextField categoryNamelbl;
    private Category category;
    private CategoryService categoryService;
    private MainController controller;

    public void editCategoryAccept(ActionEvent actionEvent) {
        if (categoryNamelbl.getText()!=null){
            categoryService.changeCategory(categoryNamelbl.getText(),category.getId());
            controller.updateTable();
            Stage stage = (Stage) categoryNamelbl.getScene().getWindow();
            stage.close();
        }
    }

    public void setTextField(Category category, CategoryService service, MainController controller){
        this.controller = controller;
        categoryService = service;
        this.category = category;
        categoryNamelbl.setText(category.getName());
    }
}
