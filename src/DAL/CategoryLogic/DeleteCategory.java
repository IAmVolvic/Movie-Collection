package DAL.CategoryLogic;

import COMMON.ApplicationException;
import DAL.ConnectionManager;
import java.sql.*;

public class DeleteCategory {
    private final ConnectionManager cm = new ConnectionManager();

    public boolean deleteCategory(int categoryId) throws ApplicationException {
        try(Connection con = cm.getConnection())
        {
            String sql = "DELETE FROM category WHERE id = ?";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setInt(1, categoryId);
            pt.executeQuery();

            return true;
        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}