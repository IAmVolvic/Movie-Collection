package BLL;

import BE.Category;
import COMMON.ApplicationException;
import DAL.CategoryLogic.DeleteCategory;
import DAL.CategoryLogic.InsertCategory;

import java.util.ArrayList;

public class CategoryService {
    private final BLLSingleton single = BLLSingleton.getInstance();
    private final InsertCategory insertCategory = new InsertCategory();
    private final DeleteCategory deleteCategory = new DeleteCategory();


    // Get all the categories
    public ArrayList<Category> getCategories(){
        return single.getCategories();
    }


    // Create a new category
    public Category createNewCategory(String categoryName) {
        try {
            return insertCategory.newCategory(categoryName);
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer", e);
        }
    }


    // Delete a new category
    public Boolean deleteCategory(int categoryId) {
        try {
            return deleteCategory.deleteCategory(categoryId);
        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer", e);
        }
    }
}
