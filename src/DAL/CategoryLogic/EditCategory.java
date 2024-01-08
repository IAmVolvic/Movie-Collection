package DAL.CategoryLogic;

import BE.Category;
import COMMON.ApplicationException;
import DAL.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class EditCategory {
    private final ConnectionManager cm = new ConnectionManager();

    public void editCategory(String newCategoryName, int oldCategoryId) throws ApplicationException {
        try(Connection con = cm.getConnection())
        {
            String sql = "UPDATE category SET name = ? WHERE id = ?";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, newCategoryName );
            pt.setInt(2,oldCategoryId);
            pt.executeUpdate();
        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}
