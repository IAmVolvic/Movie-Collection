package BLL;

import BE.Category;
import COMMON.ApplicationException;
import DAL.CategoryLogic.DeleteCategory;
import DAL.CategoryLogic.UpdateCategory;
import DAL.CategoryLogic.InsertCategory;
import javafx.collections.ObservableList;

public class CategoryService {
    private final BLLSingleton single = BLLSingleton.getInstance();
    private final InsertCategory insertCategory = new InsertCategory();
    private final DeleteCategory deleteCategory = new DeleteCategory();
    private final UpdateCategory editCategory = new UpdateCategory();


    // Get all the categories
    public ObservableList<Category> getCategories(){
        return single.getCategories();
    }


    // Create a new category
    public Category createNewCategory(String categoryName) {
        try {
            Category createCategory = insertCategory.newCategory(categoryName);
            single.addCategory(createCategory);
            return createCategory;
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer", e);
        }
    }


    // Delete a new category
    public Boolean deleteCategory(Category category) {
        try {
            single.deleteCategory(category);
            return deleteCategory.deleteCategory(category.getId());
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer", e);
        }
    }

    public void changeCategory(String newCategory, int oldCategory){
        try {
            single.editCategory(newCategory,oldCategory);
            editCategory.editCategory(newCategory,oldCategory);
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer",e);
        }
    }
}
