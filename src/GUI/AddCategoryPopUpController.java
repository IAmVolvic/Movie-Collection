package GUI;

import BE.Category;
import BLL.CategoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoryPopUpController {
    // BLL Services
    private final CategoryService categoryService = new CategoryService();

    @FXML
    private TextField categoryNamelbl;

    @FXML
    private void addCategoryAccept(ActionEvent actionEvent) {
        if (!categoryNamelbl.getText().isEmpty()){
            categoryService.createNewCategory(categoryNamelbl.getText());
            
            Stage stage = (Stage) categoryNamelbl.getScene().getWindow();
            stage.close();
        }
    }
}
