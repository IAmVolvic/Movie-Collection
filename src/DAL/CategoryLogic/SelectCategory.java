package DAL.CategoryLogic;

import BE.Category;
import COMMON.ApplicationException;
import DAL.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class SelectCategory {
    private final ConnectionManager cm = new ConnectionManager();
    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();

    public ObservableList<Category> getCategoryDB() throws ApplicationException {
        try( Connection con = cm.getConnection() )
        {
            String sql = "SELECT * FROM category";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

        while(rs.next()){
            int id              = rs.getInt("id");
            String name         = rs.getString("name");

            Category cate = new Category(id, name);
            categoryList.add(cate);
        }
        return categoryList;

        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}
