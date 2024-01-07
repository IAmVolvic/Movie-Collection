package DAL.CategoryLogic;

import BE.Category;
import COMMON.ApplicationException;
import DAL.ConnectionManager;
import java.sql.*;


public class InsertCategory {
    private final ConnectionManager cm = new ConnectionManager();

    public Category newCategory(String categoryName) throws ApplicationException {
        try(Connection con = cm.getConnection())
        {
            String sql = "INSERT INTO category(name) VALUES (?)";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setString(1, categoryName);
            int affectedRows = pt.executeUpdate();

            if (affectedRows == 0) {
                throw new ApplicationException("Creating category failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Category(generatedKeys.getInt(1), categoryName);
                }
                else {
                    throw new ApplicationException("Creating playlist failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new ApplicationException("Error in DAL layer", e);
        }
    }
}