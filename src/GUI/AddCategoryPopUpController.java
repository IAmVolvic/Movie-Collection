package GUI;

import BE.Category;
import DAL.Categories.AddCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoryPopUpController {
    @FXML
    private TextField categoryNamelbl;

    @FXML
    private void addCategoryAccept(ActionEvent actionEvent) {
        if (!categoryNamelbl.getText().isEmpty()){
            Category category = new Category(categoryNamelbl.getText());
            AddCategory addCategory = new AddCategory();
            addCategory.addCategoryToDB(category);
            Stage stage = (Stage) categoryNamelbl.getScene().getWindow();
            stage.close();
        }
    }
}
