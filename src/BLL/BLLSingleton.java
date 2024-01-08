package BLL;

import BE.Category;
import COMMON.ApplicationException;
import DAL.CategoryLogic.SelectCategory;

import java.util.ArrayList;

public class BLLSingleton {
    // Single instance of GUISingleton
    private static final BLLSingleton instance = new BLLSingleton();

    // Global States
    private ArrayList<Category> categories = new ArrayList<>();

    // DAL Ini
    private final SelectCategory selectCategory = new SelectCategory();


    // Private constructor to prevent instantiation from outside
    private BLLSingleton() {
        // Initialize
        categoriesInitialize();
    }


    private void categoriesInitialize() {
        try {
            categories = selectCategory.getCategoryDB();

        } catch (ApplicationException e) {
            throw new RuntimeException("Error in BLL layer -> singleton", e);
        }
    }


    public static BLLSingleton getInstance() {
        return instance;
    }


    public ArrayList<Category> getCategories(){
        return categories;
    }
    public void addCategory(Category newCategory){
        categories.add(newCategory);
    }
}
